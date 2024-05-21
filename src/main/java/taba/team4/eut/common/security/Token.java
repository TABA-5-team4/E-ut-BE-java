package taba.team4.eut.common.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@Builder
@Entity
//@RedisHash("refreshToken")
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @JsonIgnore
    private Long id;

    private String refresh_token;
    @TimeToLive(unit = TimeUnit.SECONDS)
    private Integer expiration;

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}

