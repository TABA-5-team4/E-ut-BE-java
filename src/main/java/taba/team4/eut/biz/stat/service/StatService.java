package taba.team4.eut.biz.stat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import taba.team4.eut.biz.stat.dto.response.TodayStatDto;
import taba.team4.eut.biz.stat.entity.StatEntity;
import taba.team4.eut.biz.stat.repository.StatRepository;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.biz.user.repository.UserRepository;
import taba.team4.eut.common.security.SecurityUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatService {

    private final UserRepository userRepository;
    private final StatRepository statRepository;

    public TodayStatDto getTodayStat(String date) {
        // 사용자 정보 조회
        Optional<UserEntity> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhone);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        // 오늘의 통계 조회
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate time = LocalDate.parse(date, formatter);
        Optional<List<StatEntity>> statEntityList = statRepository.findByUserAndStatDate(user.get(), time);
        if (statEntityList.isEmpty() || statEntityList.get().isEmpty()) {
            log.info("오늘의 통계가 없습니다.");
            throw new RuntimeException("오늘의 통계가 없습니다.");
        }
        log.info("오늘의 통계 조회 성공, 통계 개수 : {}", statEntityList.get().size());

        // 감정 통계
        Object[] emotionStat = statRepository.findAvgEmotionScoreByStatDate(user.get().getMemberId(), time);
        log.info("오늘의 감정 통계 : {}", emotionStat);



        statEntityList.get().forEach(statEntity -> {
            log.info("오늘의 통계 : {}", statEntity);
        });

        return new TodayStatDto();
    }
}
