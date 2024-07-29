package taba.team4.eut.biz.character.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class CharacterRequestDTO {
    private Long characterId;
    private String characterCode;
    private String characterName;
    private MultipartFile voiceFile;
}
