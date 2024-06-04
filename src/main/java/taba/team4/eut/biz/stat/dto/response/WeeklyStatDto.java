package taba.team4.eut.biz.stat.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import taba.team4.eut.biz.stat.dto.AverageStatDto;
import taba.team4.eut.biz.stat.dto.ScreenTimeWeeklyDto;

@Getter
@Setter
@NoArgsConstructor
public class WeeklyStatDto {
    // 주간 평균 사용 시간
    private Long avgUsageTimeSecond;
    // 주간 평균 감정
    private AverageStatDto avgEmotion = new AverageStatDto();
    // 주간 감정 최대값
//    private Map<String, Double> maxEmotion;
    // 주간 감정 통계(요일단위)
    private ScreenTimeWeeklyDto screenTimeWeekly = new ScreenTimeWeeklyDto();
    //주간 부정 표현 비율
    private ScreenTimeWeeklyDto negativeExpRate = new ScreenTimeWeeklyDto();
    // 주간 부정 표현 평균
    private Long avgNegativeExpRate;
    // 지난 주 대비 변화량
    private Long changeUsageTimeSecond;

    public void setAvgNegativeExpRate() {
       int count = 0;
       long sum = 0L;

       if (this.negativeExpRate.getMon() != 0L) {
            count++;
            sum += this.negativeExpRate.getMon();
       }
        if (this.negativeExpRate.getTue() != 0L) {
              count++;
              sum += this.negativeExpRate.getTue();
        }
        if (this.negativeExpRate.getWed() != 0L) {
              count++;
              sum += this.negativeExpRate.getWed();
        }
        if (this.negativeExpRate.getThu() != 0L) {
              count++;
              sum += this.negativeExpRate.getThu();
        }
        if (this.negativeExpRate.getFri() != 0L) {
              count++;
              sum += this.negativeExpRate.getFri();
        }
        if (this.negativeExpRate.getSat() != 0L) {
              count++;
              sum += this.negativeExpRate.getSat();
        }
        if (this.negativeExpRate.getSun() != 0L) {
              count++;
              sum += this.negativeExpRate.getSun();
        }
       this.avgNegativeExpRate = sum / count;
    }
}
