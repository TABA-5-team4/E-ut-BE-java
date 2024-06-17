package taba.team4.eut.biz.stat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import taba.team4.eut.biz.chat.dto.SentimentDataDto;
import taba.team4.eut.biz.stat.dto.ScreenTimeDailyDto;
import taba.team4.eut.biz.stat.entity.StatEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodayStatDto {
    // todo 스트링 또는 SummaryDto
    List<String> summaryDay = new ArrayList<>();
    List<String> summaryEvening = new ArrayList<>();
    List<SentimentDataDto> sentimentAnalysis;
    ScreenTimeDailyDto dailyScreenTime = new ScreenTimeDailyDto();
    Long totalScreenTimeSecond;

    public void addSummaryDay(String summary) {
        this.summaryDay.add(summary);
    }

    public void addSummaryEvening(String summary) {
        this.summaryEvening.add(summary);
    }

    public void setDailyScreenTimeMap(StatEntity entity) {
        // entity.getCreatedAt() 값이 0 ~24 사이 시간 중 2 시간 마다
        switch (entity.getCreatedAt().getHour() / 2) {
            case 0 -> // 0 ≤ value < 2
                    this.dailyScreenTime.t0_2 += entity.getUsageTimeSecond();
            case 1 -> // 2 ≤ value < 4
                    this.dailyScreenTime.t2_4 += entity.getUsageTimeSecond();
            case 2 -> // 4 ≤ value < 6
                    this.dailyScreenTime.t4_6 += entity.getUsageTimeSecond();
            case 3 -> // 6 ≤ value < 8
                    this.dailyScreenTime.t6_8 += entity.getUsageTimeSecond();
            case 4 -> // 8 ≤ value < 10
                    this.dailyScreenTime.t8_10 +=  entity.getUsageTimeSecond();
            case 5 -> // 10 ≤ value < 12
                    this.dailyScreenTime.t10_12 += entity.getUsageTimeSecond();
            case 6 -> // 12 ≤ value < 14
                    this.dailyScreenTime.t12_14 += entity.getUsageTimeSecond();
            case 7 -> // 14 ≤ value < 16
                    this.dailyScreenTime.t14_16 += entity.getUsageTimeSecond();
            case 8 -> // 16 ≤ value < 18
                    this.dailyScreenTime.t16_18 += entity.getUsageTimeSecond();
            case 9 -> // 18 ≤ value < 20
                    this.dailyScreenTime.t18_20 += entity.getUsageTimeSecond();
            case 10 -> // 20 ≤ value < 22
                    this.dailyScreenTime.t20_22 += entity.getUsageTimeSecond();
            case 11 -> // 22 ≤ value < 24
                    this.dailyScreenTime.t22_24 += entity.getUsageTimeSecond();
            default -> System.out.println("Value is out of range");
        }
    }
}
