package taba.team4.eut.biz.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "REFRESH")
    private String refresh;

    @Column(name = "EXPIRATION")
    private String expiration;
}
