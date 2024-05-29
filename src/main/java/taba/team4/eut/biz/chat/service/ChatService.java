package taba.team4.eut.biz.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taba.team4.eut.aws.s3.AwsS3Dto;
import taba.team4.eut.aws.s3.AwsS3Service;
import taba.team4.eut.biz.chat.dto.ChatRequestDto;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.biz.user.repository.UserRepository;
import taba.team4.eut.common.security.SecurityUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final String BASE_UPLOAD_DIR = "chat_voice";
    private final String FAST_API_URL = "http://127.0.0.1:8000/chat";
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    public void stt(ChatRequestDto chatRequestDto) throws IOException {
        // 사용자 정보 조회
        Optional<UserEntity> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhone);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }
        // 파일 경로 설정
        LocalDate now = LocalDate.now();
        String today = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String dirName = BASE_UPLOAD_DIR + "/" + user.get().getMemberId().toString() +  "/" + today;

        // 파일 S3 업로드
        AwsS3Dto awsS3Dto = awsS3Service.upload(chatRequestDto.getVoiceFile(), dirName);

        // FastAPI 호출

    }
}
