package taba.team4.eut.biz.stat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import taba.team4.eut.biz.stat.entity.StatEntity;

import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
public class ScreenTimeMonthlyDto {
    private Long week1 = 0L;
    private Long week2 = 0L;
    private Long week3 = 0L;
    private Long week4 = 0L;
    private Long week5 = 0L;

    // 월간 사용 시간
    public void addScreenTime(List<StatEntity> entityList) {
        for (StatEntity entity : entityList) {
            int weekOfMonth = entity.getStatDate().get(WeekFields.of(Locale.getDefault()).weekOfMonth());
            switch (weekOfMonth) {
                case 1:
                    week1 += entity.getUsageTimeSecond();
                    break;
                case 2:
                    week2 += entity.getUsageTimeSecond();
                    break;
                case 3:
                    week3 += entity.getUsageTimeSecond();
                    break;
                case 4:
                    week4 += entity.getUsageTimeSecond();
                    break;
                case 5:
                    week5 += entity.getUsageTimeSecond();
                    break;
            }
        }
    }

    // 월간 부정 표현 비율
    // 횟수만큼 나눠줘야 함
    public void addNegativeExpRate(List<StatEntity> entityList) {

        int week1Count = 0;
        int week2Count = 0;
        int week3Count = 0;
        int week4Count = 0;
        int week5Count = 0;

        for (StatEntity entity : entityList) {
            int weekOfMonth = entity.getStatDate().get(WeekFields.of(Locale.getDefault()).weekOfMonth());
            switch (weekOfMonth) {
                case 1:
                    week1 += entity.getNegativeExpRate();
                    week1Count++;
                    break;
                case 2:
                    week2 += entity.getNegativeExpRate();
                    week2Count++;
                    break;
                case 3:
                    week3 += entity.getNegativeExpRate();
                    week3Count++;
                    break;
                case 4:
                    week4 += entity.getNegativeExpRate();
                    week4Count++;
                    break;
                case 5:
                    week5 += entity.getNegativeExpRate();
                    week5Count++;
                    break;
            }
        }

        if (week1Count != 0) {
            week1 /= week1Count;
        }
        if (week2Count != 0) {
            week2 /= week2Count;
        }
        if (week3Count != 0) {
            week3 /= week3Count;
        }
        if (week4Count != 0) {
            week4 /= week4Count;
        }
        if (week5Count != 0) {
            week5 /= week5Count;
        }
    }

}
