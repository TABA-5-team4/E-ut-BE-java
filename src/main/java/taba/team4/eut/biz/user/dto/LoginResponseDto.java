package taba.team4.eut.biz.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import taba.team4.eut.biz.user.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {
    private String access_token;
    private String refresh_token;
    private String phone;
    private Character memberType;



    public LoginResponseDto(String access_token, String refresh_token, String phone, Character memberType) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.phone = phone;
        this.memberType = memberType;
    }

    public LoginResponseDto(UserEntity user, String access_token, String refresh_token) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.phone = user.getPhone();
        this.memberType = user.getMemberType();

    }

}
