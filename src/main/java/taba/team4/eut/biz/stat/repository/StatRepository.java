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
            "   AVG(happiness_score) as happinessScore, " +
            "   AVG(panic_score) as panicScore, " +
            "   AVG(neutral_score) as neutralScore, " +
            "   AVG(anxiety_score) as anxietyScore, " +
            "   AVG(anger_score) as angerScore, " +
            "   AVG(sadness_score) as sadnessScore, " +
            "   AVG(disgust_score) as disgustScore " +
            "FROM USER_STATISTICS " +
            "WHERE MEMBER_ID = :memberId " +
            "AND STAT_DATE = :statDate", nativeQuery = true)
    Object[] findAvgEmotionScoreByStatDate(@Param("memberId") Long MemberId, @Param("statDate") LocalDate date);
}
