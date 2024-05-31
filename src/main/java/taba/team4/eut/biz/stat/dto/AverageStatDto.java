package taba.team4.eut.biz.stat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import taba.team4.eut.biz.stat.repository.AvgStatInterface;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AverageStatDto {
    private Double happinessScore;
    private Double panicScore;
    private Double neutralScore;
    private Double anxietyScore;
    private Double angerScore;
    private Double sadnessScore;
    private Double disgustScore;
    private Double negativeExpRate;

    public AverageStatDto(AvgStatInterface avgStatInterface) {
        this.happinessScore = avgStatInterface.getHappinessScore();
        this.panicScore = avgStatInterface.getPanicScore();
        this.neutralScore = avgStatInterface.getNeutralScore();
        this.anxietyScore = avgStatInterface.getAnxietyScore();
        this.angerScore = avgStatInterface.getAngerScore();
        this.sadnessScore = avgStatInterface.getSadnessScore();
        this.disgustScore = avgStatInterface.getDisgustScore();
//        this.negativeExpRate = avgStatInterface.getNegativeExpRate();
    }
}
