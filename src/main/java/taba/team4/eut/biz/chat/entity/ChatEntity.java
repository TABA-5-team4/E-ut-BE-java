package taba.team4.eut.biz.chat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.team4.eut.biz.chat.enums.Sender;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.common.dto.BaseEntity;
import taba.team4.eut.common.dto.BaseModel;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CHAT")
public class ChatEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ID")
    private Long chatId;

    @Column(name = "SENDER")
    @Enumerated(EnumType.STRING)
    private Sender sender;

    @Column(name = "MESSAGE")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference // 순환 참조 방지
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private UserEntity user;

    @OneToOne(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ChatVoiceEntity chatVoice;


    @Override
    public <M extends BaseModel> M toModel() {
        return null;
    }
}
