package taba.team4.eut.biz.stat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SummaryDto {
    private String title;
    private List<String> content;
    private LocalDateTime time;
    private Long audioLength;
}
