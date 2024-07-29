package taba.team4.eut.biz.character.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.common.dto.BaseEntity;
import taba.team4.eut.common.dto.BaseModel;

@Getter
@ToString
@Builder
@Entity
@Table(name = "CHARACTER_PROFILE")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHARACTER_ID")
    private Long characterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "MEMBER_ID")
    private UserEntity user;

    @Column(name = "CHARACTER_CODE")
    private String characterCode;

    @Column(name = "CHARACTER_NAME")
    private String characterName;

    @Column(name= "VOICE_ID")
    private String voiceId;

    @Override
    public <M extends BaseModel> M toModel() {
        return null;
    }
}
