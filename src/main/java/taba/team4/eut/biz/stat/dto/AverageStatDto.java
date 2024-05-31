package taba.team4.eut.biz.stat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AverageStatDto {
    private Double happinessScore;
    private Double panicScore;
    private Double neutralScore;
    private Double anxietyScore;
    private Double angerScore;
    private Double sadnessScore;
    private Double disgustScore;
    private Double negativeExpRate;
}
