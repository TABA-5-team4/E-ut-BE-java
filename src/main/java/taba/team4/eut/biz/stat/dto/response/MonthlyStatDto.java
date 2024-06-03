package taba.team4.eut.biz.stat.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import taba.team4.eut.biz.stat.dto.AverageStatDto;
import taba.team4.eut.biz.stat.dto.ScreenTimeMonthlyDto;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyStatDto {
    // 월간 평균 사용 시간
    private Long avgUsageTimeSecond;
    // 월간 평균 감정
    private AverageStatDto avgEmotion = new AverageStatDto();
    // 월간 감정 통계(주단위)
    private ScreenTimeMonthlyDto screenTimeMonthly = new ScreenTimeMonthlyDto();
    //월간 부정 표현 비율
    private ScreenTimeMonthlyDto negativeExpRate = new ScreenTimeMonthlyDto();
}
