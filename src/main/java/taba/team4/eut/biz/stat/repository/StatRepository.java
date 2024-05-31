package taba.team4.eut.biz.stat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import taba.team4.eut.biz.stat.entity.StatEntity;
import taba.team4.eut.biz.user.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatRepository extends JpaRepository<StatEntity, Long> {

    Optional<List<StatEntity>> findByUserAndStatDate(UserEntity user, LocalDate date);

    // 특정 날짜 감정 평균 점수 조회
    @Query(value = "" +
            "SELECT " +
            "   AVG(happiness_score) * 100 as happinessScore, " +
            "   AVG(panic_score) * 100 as panicScore, " +
            "   AVG(neutral_score) * 100 as neutralScore, " +
            "   AVG(anxiety_score) * 100 as anxietyScore, " +
            "   AVG(anger_score) * 100 as angerScore, " +
            "   AVG(sadness_score) * 100 as sadnessScore, " +
            "   AVG(disgust_score) * 100 as disgustScore, " +
            "   SUM(USAGE_TIME_SECOND) as usageTimeSecond " +
            "FROM USER_STATISTICS " +
            "WHERE MEMBER_ID = :memberId " +
            "AND STAT_DATE = :statDate", nativeQuery = true)
    AvgStatInterface findAvgEmotionScoreByStatDate(@Param("memberId") Long MemberId, @Param("statDate") LocalDate date);

    // 주간 통계 조회
    @Query(value = "" +
            "SELECT * ," +

            "FROM USER_STATISTICS " +
            "WHERE MEMBER_ID = :memberId " +
            "AND STAT_DATE BETWEEN :startDate AND :endDate", nativeQuery = true)
    Optional<List<StatEntity>> findUserStatWeekly(@Param("memberId") Long MemberId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // 주간 감정 통계
    @Query(value = "" +
            "SELECT " +
            "   AVG(happiness_score) * 100 as happinessScore, " +
            "   AVG(panic_score) * 100 as panicScore, " +
            "   AVG(neutral_score) * 100 as neutralScore, " +
            "   AVG(anxiety_score) * 100 as anxietyScore, " +
            "   AVG(anger_score) * 100 as angerScore, " +
            "   AVG(sadness_score) * 100 as sadnessScore, " +
            "   AVG(disgust_score) * 100 as disgustScore, " +
            "   SUM(USAGE_TIME_SECOND) as usageTimeSecond " +
            "FROM USER_STATISTICS " +
            "WHERE MEMBER_ID = :memberId " +
            "AND STAT_DATE BETWEEN :startDate AND :endDate", nativeQuery = true)
    AvgStatInterface findUserStatWeeklySentiment(@Param("memberId") Long MemberId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
