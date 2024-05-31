package taba.team4.eut.biz.stat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
