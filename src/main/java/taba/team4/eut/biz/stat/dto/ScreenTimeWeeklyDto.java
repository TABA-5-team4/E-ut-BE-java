package taba.team4.eut.biz.stat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import taba.team4.eut.biz.stat.entity.StatEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScreenTimeWeeklyDto {
    private Long mon = 0L;
    private Long tue = 0L;
    private Long wed = 0L;
    private Long thu = 0L;
    private Long fri = 0L;
    private Long sat = 0L;
    private Long sun = 0L;

    // 주간 사용 시간
    public void addScreenTime(List<StatEntity> entityList) {
        for (StatEntity entity : entityList) {
            switch (entity.getStatDate().getDayOfWeek().getValue()) {
                case 1:
                    mon += entity.getUsageTimeSecond();
                    break;
                case 2:
                    tue += entity.getUsageTimeSecond();
                    break;
                case 3:
                    wed += entity.getUsageTimeSecond();
                    break;
                case 4:
                    thu += entity.getUsageTimeSecond();
                    break;
                case 5:
                    fri += entity.getUsageTimeSecond();
                    break;
                case 6:
                    sat += entity.getUsageTimeSecond();
                    break;
                case 7:
                    sun += entity.getUsageTimeSecond();
                    break;
            }
        }
    }

    // 주간 부정 표현 비율
    // 횟수만큼 나눠줘야 함
    public void addNegativeExpRate(List<StatEntity> entityList) {

        int monCount = 0;
        int tueCount = 0;
        int wedCount = 0;
        int thuCount = 0;
        int friCount = 0;
        int satCount = 0;
        int sunCount = 0;

        for (StatEntity entity : entityList) {
            switch (entity.getStatDate().getDayOfWeek().getValue()) {
                case 1:
                    mon += entity.getNegativeExpRate();
                    monCount++;
                    break;
                case 2:
                    tue += entity.getNegativeExpRate();
                    tueCount++;
                    break;
                case 3:
                    wed += entity.getNegativeExpRate();
                    wedCount++;
                    break;
                case 4:
                    thu += entity.getNegativeExpRate();
                    thuCount++;
                    break;
                case 5:
                    fri += entity.getNegativeExpRate();
                    friCount++;
                    break;
                case 6:
                    sat += entity.getNegativeExpRate();
                    satCount++;
                    break;
                case 7:
                    sun += entity.getNegativeExpRate();
                    sunCount++;
                    break;
            }
        }

        // 평균내기 count로 나눠줌
        if (monCount != 0) {
            mon /= monCount;
        }
        if (tueCount != 0) {
            tue /= tueCount;
        }
        if (wedCount != 0) {
            wed /= wedCount;
        }
        if (thuCount != 0) {
            thu /= thuCount;
        }
        if (friCount != 0) {
            fri /= friCount;
        }
        if (satCount != 0) {
            sat /= satCount;
        }
        if (sunCount != 0) {
            sun /= sunCount;
        }

    }
}
