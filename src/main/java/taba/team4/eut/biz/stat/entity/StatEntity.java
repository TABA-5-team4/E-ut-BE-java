package taba.team4.eut.biz.stat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.common.dto.BaseEntity;
import taba.team4.eut.common.dto.BaseModel;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USER_STATISTICS")
@Builder
public class StatEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAT_ID")
    private Long statId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private UserEntity user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STAT_DATE")
    private LocalDateTime statDate;

    @Column(name = "SUMMARY")
    private String summary;

    @Column(name = "USAGE_TIME_SECOND")
    private Long usageTimeSecond;

    @Column(name = "HAPPINESS_SCORE")
    private Double happinessScore;

    @Column(name = "PANIC_SCORE")
    private Double panicScore;

    @Column(name = "NEUTRAL_SCORE")
    private Double neutralScore;

    @Column(name = "ANXIETY_SCORE")
    private Double anxietyScore;

    @Column(name = "ANGER_SCORE")
    private Double angerScore;

    @Column(name = "SADNESS_SCORE")
    private Double sadnessScore;

    @Column(name = "DISGUST_SCORE")
    private Double disgustScore;

    @Column(name = "NEGATIVE_EXP_RATE")
    private Long negativeExpRate;




    @Override
    public <M extends BaseModel> M toModel() {
        return null;
    }
}
