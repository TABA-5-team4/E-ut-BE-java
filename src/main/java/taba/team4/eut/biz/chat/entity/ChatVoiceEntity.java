package taba.team4.eut.biz.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.team4.eut.common.dto.BaseEntity;
import taba.team4.eut.common.dto.BaseModel;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CHAT_VOICE")
public class ChatVoiceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long fileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ID", nullable = false)
    private ChatEntity chat;




    @Override
    public <M extends BaseModel> M toModel() {
        return null;
    }
}
