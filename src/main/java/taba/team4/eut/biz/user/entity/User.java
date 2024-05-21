package taba.team4.eut.biz.user.entity;

import jakarta.persistence.*;
import lombok.*;
import taba.team4.eut.biz.user.dto.UserModel;
import taba.team4.eut.common.dto.BaseEntity;
import taba.team4.eut.common.dto.BaseModel;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "MEMBER")
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Character memberType;

    private String email;

    public User(UserModel model) {

    }

    @Override
    public <M extends BaseModel> M toModel() {
        return null;
    }
}
