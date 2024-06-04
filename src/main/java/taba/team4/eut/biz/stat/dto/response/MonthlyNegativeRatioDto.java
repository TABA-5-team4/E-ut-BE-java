package taba.team4.eut.biz.stat.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import taba.team4.eut.biz.stat.dto.AverageStatDto;
import taba.team4.eut.biz.stat.repository.MonthlyNegativeExpInterface;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyNegativeRatioDto {
    private List<DailyNegativityRatioDto> dailyNegativityRatioList = new ArrayList<>();

    public void addDailyNegativityRatio(MonthlyNegativeExpInterface monthlyNegativeExpInterface) {
        dailyNegativityRatioList.add(new DailyNegativityRatioDto(monthlyNegativeExpInterface.getStatDate(), monthlyNegativeExpInterface.getAvgNegativeExpRate()));
    }
}
