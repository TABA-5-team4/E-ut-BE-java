package taba.team4.eut.biz.character.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import taba.team4.eut.biz.character.dto.CharacterDTO;
import taba.team4.eut.biz.character.dto.CharacterRequestDTO;
import taba.team4.eut.biz.character.entity.CharacterEntity;
import taba.team4.eut.biz.character.repository.CharacterRepository;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.biz.user.repository.UserRepository;
import taba.team4.eut.common.security.SecurityUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service

public class CharacterService {

    private static final String ELEVEN_LABS_API_BASE = "https://api.eleven-labs.com";

    private final CharacterRepository characterRepository;
    private final UserRepository userRepository;
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    public CharacterService(CharacterRepository characterRepository, UserRepository userRepository, WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
        this.webClientBuilder = webClientBuilder.baseUrl(ELEVEN_LABS_API_BASE);
        this.objectMapper = objectMapper;
    }

    public List<CharacterDTO> getCharacters() {
        // 사용자 정보 조회
        Optional<UserEntity> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhone);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        // 기본 캐릭터 정보
        List<CharacterDTO> characterDTOList = new ArrayList<>();

        characterDTOList.add(CharacterDTO.builder()
                .characterId(null)
                .memberId(user.get().getMemberId())
                .characterName("영수")
                .voiceId("pNInz6obpgDQGcFmaJgB") // elevenLabs Adam voice ID
                .build());

        characterDTOList.add(CharacterDTO.builder()
                .characterId(null)
                .memberId(user.get().getMemberId())
                .characterName("순옥")
                .voiceId("MF3mGyEYCl7XYWbV9V6O") // elevenLabs Elli voice ID
                .build());

        List<CharacterEntity> entityList = characterRepository.findByUser(user.get());

        if (entityList != null) {
            for (CharacterEntity entity : entityList) {
                characterDTOList.add(CharacterDTO.builder()
                        .characterId(entity.getCharacterId())
                        .memberId(entity.getUser().getMemberId())
                        .characterName(entity.getCharacterName())
                        .voiceId(entity.getVoiceId())
                        .build());
            }
        }

        return characterDTOList;

    }

    // 캐릭터 등록하기
    @Transactional
    public CharacterDTO createCharacter(CharacterRequestDTO requestDTO) throws IOException {
        Optional<UserEntity> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhone);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        if (requestDTO.getCharacterName() == null || requestDTO.getVoiceFile().isEmpty()) {
            throw new IllegalArgumentException("캐릭터 이름 또는 음성 파일이 없습니다.");
        }

        try {
            // elevenLabs 등록하기 API 호출
            WebClient webClient = webClientBuilder.build();


            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            formData.add("name", requestDTO.getCharacterName());
            formData.add("files", requestDTO.getVoiceFile().getResource());
            formData.add("description", user.get().getMemberId() + "_" + requestDTO.getCharacterName());
            formData.add("labels", "labels");

            String response = webClient.post()
                    .uri("/v1/voices/add")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(formData))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new RuntimeException("API call failed: " + errorBody)))
                    )
                    .bodyToMono(String.class)
                    .block();
            log.info("elevenLabs 등록하기 API 호출 결과 : {}", response);
            // 응답 매핑
            String voiceId = objectMapper.readValue(response, Map.class).get("voice_id").toString();

            CharacterEntity entity = CharacterEntity.builder()
                    .user(user.get())
                    .characterName(requestDTO.getCharacterName())
                    .voiceId(voiceId)
                    .build();

            CharacterEntity savedEntity = characterRepository.save(entity);

            return CharacterDTO.builder()
                    .characterId(savedEntity.getCharacterId())
                    .memberId(savedEntity.getUser().getMemberId())
                    .characterName(savedEntity.getCharacterName())
                    .voiceId(savedEntity.getVoiceId())
                    .build();



        } catch (WebClientRequestException e) {
            log.error("API 호출 중 오류 발생. Response body: {}", e.getMessage());
            throw new RuntimeException("API 호출 실패", e);
        }

    }
}
