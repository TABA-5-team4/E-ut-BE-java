package taba.team4.eut.biz.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SttChatResponseDto {
    private String stt_result;
    private String gpt_response;
    private Long audio_length;
    private List<SentimentDataDto> sentiment_analysis;

    // 라벨로 스코어 찾기 함수
    public Double filterByLabel(String label) {
        SentimentDataDto dto = sentiment_analysis.stream().filter(data -> data.getLabel().equals(label)).findFirst().orElse(null);
        if (dto == null) {
            return 0D;
        }

        return dto.getScore();
    }

    // 부정 표현 비율 반환 함수
    public Long getNegativeRatio() {
        double negative = filterByLabel("분노") + filterByLabel("혐오") + filterByLabel("슬픔") + filterByLabel("불안");
//        Long total = filterByLabel("행복") + filterByLabel("중립") + filterByLabel("당황") + negative;
        return Math.round(negative * 100);
    }

}
