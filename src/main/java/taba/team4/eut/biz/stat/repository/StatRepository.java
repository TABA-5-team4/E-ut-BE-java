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

    // 특정 기간 통계 데이터 조회
    @Query(value = "" +
            "SELECT * " +
            "FROM USER_STATISTICS " +
            "WHERE MEMBER_ID = :memberId " +
            "AND STAT_DATE BETWEEN :startDate AND :endDate", nativeQuery = true)
    Optional<List<StatEntity>> findUserStatBetweenDates(@Param("memberId") Long MemberId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // 특정 기간 감정 통계
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
    AvgStatInterface findUserStatBetweenDatesSentiment(@Param("memberId") Long MemberId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // insert query for random stat in month
    // values is random value
    @Query(value = "" +
            "INSERT INTO USER_STATISTICS " +
            "(MEMBER_ID, STAT_DATE, HAPPINESS_SCORE, PANIC_SCORE, NEUTRAL_SCORE, ANXIETY_SCORE, ANGER_SCORE, SADNESS_SCORE, DISGUST_SCORE, USAGE_TIME_SECOND, SUMMARY, NEGATIVE_EXP_RATE, CREATED_AT) " +
            "VALUES" +
            "( " +
            "    :memberId, " +
            "    DATE(CONCAT('2024-' , :monthNum , '-', LPAD(FLOOR(RAND() * 30) + 1, 2, '0'))), " +
            "    :rand0 , " +
            "    :rand1 , " +
            "    :rand2 , " +
            "    :rand3 , " +
            "    :rand4 , " +
            "    :rand5 , " +
            "    :rand6 , " +
            "    FLOOR(RAND() * 100), " +
            "    :randomSummary," +
            "    :negativeExpRate," +
            "   DATE_ADD( " +
            "        CONCAT('2024-' , :monthNum, '-01 00:00:00'), " +
            "        INTERVAL FLOOR(RAND() * 28) DAY " +
            "    ) + INTERVAL FLOOR(RAND() * 24) HOUR " +
            "    + INTERVAL FLOOR(RAND() * 60) MINUTE " +
            "    + INTERVAL FLOOR(RAND() * 60) SECOND " +
            "); ", nativeQuery = true)
    void insertRandomStat(
            @Param("memberId")Long memberId,
            @Param("monthNum") String monthNum,
            @Param("randomSummary") String randomSummary,
            @Param("rand0") Double rand0,
            @Param("rand1") Double rand1,
            @Param("rand2") Double rand2,
            @Param("rand3") Double rand3,
            @Param("rand4") Double rand4,
            @Param("rand5") Double rand5,
            @Param("rand6") Double rand6,
            @Param("negativeExpRate") int negativeExpRate
            );
}
