package taba.team4.eut.biz.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import taba.team4.eut.biz.chat.dto.ChatRequestDto;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.biz.user.repository.UserRepository;
import taba.team4.eut.common.security.SecurityUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final String BASE_UPLOAD_DIR = "chat_voice";
    private final UserRepository userRepository;
    public void stt(ChatRequestDto chatRequestDto) {
//        Optional<UserEntity> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhone);
//
//        if (user.isEmpty()) {
//            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
//        }

        LocalDate now = LocalDate.now();
        String today = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

//        String dirName = BASE_UPLOAD_DIR + "/" + user.get().getMemberId().toString() +  "/" + today;
    }
}
