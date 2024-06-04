package taba.team4.eut.biz.stat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DailyNegativityRatioDto {
    private LocalDate date;
    private Double negativityRatio;

    public DailyNegativityRatioDto(LocalDate date, Double negativityRatio) {
        this.date = date;
        this.negativityRatio = negativityRatio;
    }
}
