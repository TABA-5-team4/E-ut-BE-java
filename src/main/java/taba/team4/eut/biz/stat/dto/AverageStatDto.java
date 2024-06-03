package taba.team4.eut.biz.stat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import taba.team4.eut.biz.stat.repository.AvgStatInterface;

import java.util.HashMap;
import java.util.Map;

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
    private Long negativeExpRate;

    public AverageStatDto(AvgStatInterface avgStatInterface) {
        this.happinessScore = avgStatInterface.getHappinessScore();
        this.panicScore = avgStatInterface.getPanicScore();
        this.neutralScore = avgStatInterface.getNeutralScore();
        this.anxietyScore = avgStatInterface.getAnxietyScore();
        this.angerScore = avgStatInterface.getAngerScore();
        this.sadnessScore = avgStatInterface.getSadnessScore();
        this.disgustScore = avgStatInterface.getDisgustScore();
        this.negativeExpRate = getNegativeRatio(avgStatInterface);
    }

    // 부정 표현 비율 반환 함수
    public Long getNegativeRatio(AvgStatInterface avgStatInterface) {
        // 분노 + 혐오 + 슬픔 + 불안
        double negative = avgStatInterface.getAngerScore() + avgStatInterface.getDisgustScore() + avgStatInterface.getSadnessScore() + avgStatInterface.getPanicScore();

        return Math.round(negative / 4);
    }

    public Map.Entry<String, Double> getMaxScore() {
        Map<String, Double> scores = new HashMap<>();
        scores.put("행복", happinessScore);
        scores.put("당황", panicScore);
        scores.put("중립", neutralScore);
        scores.put("불안", anxietyScore);
        scores.put("분노", angerScore);
        scores.put("슬픔", sadnessScore);
        scores.put("혐오", disgustScore);

        Map.Entry<String, Double> maxEntry = null;

        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }

        return maxEntry;
    }
}
