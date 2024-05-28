package taba.team4.eut.biz.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import taba.team4.eut.biz.user.dto.UserModel;
import taba.team4.eut.common.dto.BaseEntity;
import taba.team4.eut.common.dto.BaseModel;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Setter
@Table(name = "MEMBER")
@Builder
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "PHONE",nullable = false)
    private String phone;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "MEMBER_TYPE",nullable = false)
    @ColumnDefault("P")
    private Character memberType;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ROLE")
    private String role;

    @ManyToMany
    @JoinTable(
            name = "PARENT_CHILD_MAPPING",
            joinColumns = @JoinColumn(name = "PARENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID")
    )
    private Set<UserEntity> parent = new HashSet<>();

    @ManyToMany(mappedBy = "parent")
    private Set<UserEntity> child = new HashSet<>();

    public UserEntity(UserModel model) {

    }

    @Override
    public <M extends BaseModel> M toModel() {
        return null;
    }
}
