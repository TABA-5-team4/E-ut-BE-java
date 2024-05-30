package taba.team4.eut.biz.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import taba.team4.eut.aws.s3.AwsS3Dto;
import taba.team4.eut.aws.s3.AwsS3Service;
import taba.team4.eut.biz.chat.dto.ChatRequestDto;
import taba.team4.eut.biz.chat.dto.SttChatResponseDto;
import taba.team4.eut.biz.chat.entity.ChatEntity;
import taba.team4.eut.biz.chat.entity.ChatVoiceEntity;
import taba.team4.eut.biz.chat.repository.ChatRepository;
import taba.team4.eut.biz.chat.repository.ChatVoiceRepository;
import taba.team4.eut.biz.stat.entity.StatEntity;
import taba.team4.eut.biz.stat.repository.StatRepository;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.biz.user.repository.UserRepository;
import taba.team4.eut.common.security.SecurityUtil;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final String BASE_UPLOAD_DIR = "chat_voice";
    private final String FAST_API_URL = "http://127.0.0.1:8000";
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;
    private final ChatRepository chatRepository;
    private final ChatVoiceRepository chatVoiceRepository;
    private final StatRepository statRepository;
    @Transactional
    public SttChatResponseDto stt(ChatRequestDto chatRequestDto) throws IOException {
        try {
            // 사용자 정보 조회
            Optional<UserEntity> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhone);

            if (user.isEmpty()) {
                throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
            }

            // FastAPI 호출
            WebClient webClient = webClientBuilder.build();

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            MultipartFile voiceFile = chatRequestDto.getVoiceFile();
            if (voiceFile == null) {
                throw new IllegalArgumentException("음성 파일이 없습니다.");
            }
            body.add("voiceFile", Arrays.toString(voiceFile.getBytes()));

            String response = webClient.post()
                    .uri(FAST_API_URL + "/stt")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromFormData(body))
                    .retrieve() // 요청을 보내고 응답을 받아옴
                    .bodyToMono(String.class) // 응답 본문을 String 으로 받음
                    .block(); // Mono(비동기 메소드)를 동기적으로 처리
            log.info("FastAPI 호출 결과 : {}", response);
            // 응답 매핑
            SttChatResponseDto responseDto = objectMapper.readValue(response, SttChatResponseDto.class);

            // 채팅 저장
            ChatEntity chatEntity = ChatEntity.builder()
                    .user(user.get())
                    .input(responseDto.getStt_result())
                    .response(responseDto.getGpt_response())
                    .build();
            chatRepository.save(chatEntity);

            // 파일 경로 설정
            LocalDate now = LocalDate.now();
            String today = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            String dirName = BASE_UPLOAD_DIR + "/" + user.get().getMemberId().toString() +  "/" + today;

            // 파일 S3 업로드
            AwsS3Dto awsS3Dto = awsS3Service.upload(chatRequestDto.getVoiceFile(), dirName);


            // 음성 파일 저장
            ChatVoiceEntity chatVoiceEntity = ChatVoiceEntity.builder()
                    .chat(chatEntity)
                    .fileName(awsS3Dto.getKey())
                    .fileUrl(awsS3Dto.getPath())
                    .fileLength(responseDto.getAudio_length())
                    .build();
            chatVoiceRepository.save(chatVoiceEntity);
            // 통계 결과 저장
            StatEntity statEntity = StatEntity.builder()
                    .user(user.get())
                    .statDate(LocalDateTime.now())
                    .usageTimeSecond(responseDto.getAudio_length())
                    .happinessScore(responseDto.filterByLabel("행복"))
                    .panicScore(responseDto.filterByLabel("당황"))
                    .neutralScore(responseDto.filterByLabel("중립"))
                    .anxietyScore(responseDto.filterByLabel("불안"))
                    .angerScore(responseDto.filterByLabel("분노"))
                    .sadnessScore(responseDto.filterByLabel("슬픔"))
                    .disgustScore(responseDto.filterByLabel("혐오"))
                    .negativeExpRate(responseDto.getNegativeRatio())
                    .build();

            statRepository.save(statEntity);


            return responseDto;

        } catch (WebClientRequestException e) {
            throw new RuntimeException("FastAPI 호출 실패 : " + e.getMessage());
        }
    }

}
