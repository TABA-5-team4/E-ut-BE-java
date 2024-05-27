//package taba.team4.eut.common.security;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.data.redis.core.TimeToLive;
//
//import java.util.concurrent.TimeUnit;
//
//@Getter
//@Builder
//@Entity
//@Table(name = "TOKEN")
////@RedisHash("refreshToken")
//@AllArgsConstructor
//@NoArgsConstructor
//public class Token {
//
//    @Id
//    @JsonIgnore
//    @Column(name = "ID")
//    private Long id;
//
//    @Column(name = "REFRESH_TOKEN")
//    private String refresh_token;
//
//    @TimeToLive(unit = TimeUnit.SECONDS)
//    @Column(name = "EXPIRATION")
//    private Integer expiration;
//
//    public void setExpiration(Integer expiration) {
//        this.expiration = expiration;
//    }
//}
//
