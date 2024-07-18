package taba.team4.eut.biz.character.dto;

import lombok.*;
import taba.team4.eut.biz.character.entity.CharacterEntity;
import taba.team4.eut.biz.user.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterDTO {
    private Long characterId;
    private Long memberId;
    private String characterName;
    private String voiceId;

    public CharacterEntity toEntity() {
        return CharacterEntity.builder()
                .characterId(characterId)
                .user(UserEntity.builder().memberId(memberId).build())
                .characterName(characterName)
                .voiceId(voiceId)
                .build();
    }
}
