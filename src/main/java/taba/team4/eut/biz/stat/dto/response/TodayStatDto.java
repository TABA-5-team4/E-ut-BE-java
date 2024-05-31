package taba.team4.eut.biz.stat.dto.response;

import lombok.Getter;
import lombok.Setter;
import taba.team4.eut.biz.chat.dto.SentimentDataDto;
import taba.team4.eut.biz.stat.dto.SummaryDto;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TodayStatDto {
    List<SummaryDto> summary;
    List<SentimentDataDto> sentiment_analysis;
    Map<String, Long> dailyScreenTime;
    List<Long> dailyScreenTimeList;
}
