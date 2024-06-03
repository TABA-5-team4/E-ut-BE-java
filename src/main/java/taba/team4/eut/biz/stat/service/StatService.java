package taba.team4.eut.biz.stat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import taba.team4.eut.biz.chat.dto.SentimentDataDto;
import taba.team4.eut.biz.stat.dto.AverageStatDto;
import taba.team4.eut.biz.stat.dto.ScreenTimeWeeklyDto;
import taba.team4.eut.biz.stat.dto.response.TodayStatDto;
import taba.team4.eut.biz.stat.dto.response.WeeklyStatDto;
import taba.team4.eut.biz.stat.entity.StatEntity;
import taba.team4.eut.biz.stat.repository.AvgStatInterface;
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

        TodayStatDto todayStatDto = new TodayStatDto();

        // 감정 통계
        AvgStatInterface emotionStat = statRepository.findAvgEmotionScoreByStatDate(user.get().getMemberId(), time);
        todayStatDto.setSentimentAnalysis(getSentimentList(emotionStat));
        todayStatDto.setTotalScreenTimeSecond(emotionStat.getUsageTimeSecond());

        statEntityList.get().forEach(statEntity -> {

            if (statEntity.getCreatedAt().getHour() < 12) {
                // 오전 요약
                todayStatDto.addSummaryDay(statEntity.getSummary());
            } else {
                // 오후 요약
                todayStatDto.addSummaryEvening(statEntity.getSummary());
            }
           todayStatDto.setDailyScreenTimeMap(statEntity);

        });

        return todayStatDto;
    }

    public List<SentimentDataDto> getSentimentList(AvgStatInterface emotionStat) {
        // ObjectList to SentimentDataDtoList
        return List.of(
                new SentimentDataDto("행복", Math.floor(emotionStat.getHappinessScore())),
                new SentimentDataDto("불안", Math.floor(emotionStat.getAnxietyScore())),
                new SentimentDataDto("중립", Math.floor(emotionStat.getNeutralScore())),
                new SentimentDataDto("당황", Math.floor(emotionStat.getPanicScore())),
                new SentimentDataDto("분노", Math.floor(emotionStat.getAngerScore())),
                new SentimentDataDto("슬픔", Math.floor(emotionStat.getSadnessScore())),
                new SentimentDataDto("혐오", Math.floor(emotionStat.getDisgustScore()))
        );
    }

    public WeeklyStatDto getWeeklyStat(String date) {
        // 사용자 정보 조회
        Optional<UserEntity> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhone);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        // 주간 통계 조회
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate time = LocalDate.parse(date, formatter);
        LocalDate startDate = time.minusDays(time.getDayOfWeek().getValue() - 1);
        LocalDate endDate = startDate.plusDays(6);

        Optional<List<StatEntity>> statEntityList = statRepository.findUserStatWeekly(user.get().getMemberId(), startDate, endDate);
        if (statEntityList.isEmpty() || statEntityList.get().isEmpty()) {
            log.info("주간 통계가 없습니다.");
            throw new RuntimeException("주간 통계가 없습니다.");
        }
        WeeklyStatDto weeklyStatDto = new WeeklyStatDto();

        // 주간 평균 감정 통계
        AvgStatInterface emotionStatAvg = statRepository.findUserStatWeeklySentiment(user.get().getMemberId(), startDate, endDate);
        weeklyStatDto.setAvgEmotion(new AverageStatDto(emotionStatAvg));
        // 주간 평균 사용 시간
        weeklyStatDto.setAvgUsageTimeSecond(emotionStatAvg.getUsageTimeSecond() / 7);

        // 주간 사용 시간
        ScreenTimeWeeklyDto screenTimeWeeklyDto = new ScreenTimeWeeklyDto();
        screenTimeWeeklyDto.addScreenTime(statEntityList.get());
        weeklyStatDto.setScreenTimeWeekly(screenTimeWeeklyDto);

        // 주간 부정 표현 비율
        ScreenTimeWeeklyDto negativeExpRate = new ScreenTimeWeeklyDto();
        negativeExpRate.addNegativeExpRate(statEntityList.get());
        weeklyStatDto.setNegativeExpRate(negativeExpRate);


        return weeklyStatDto;
    }
}
