package taba.team4.eut.biz.user.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taba.team4.eut.biz.user.dto.LoginResponseDto;
import taba.team4.eut.biz.user.dto.UserDto;
import taba.team4.eut.biz.user.service.UserService;
import taba.team4.eut.common.controller.BaseApiController;
import taba.team4.eut.common.controller.BaseApiDto;
import taba.team4.eut.common.exception.BizException;

@RestController
@RequestMapping("/api/v1")
public class UserController extends BaseApiController<BaseApiDto<?>> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/join")
    public ResponseEntity<BaseApiDto<?>> join(@RequestBody UserDto userDto) {
        try {
            userService.join(userDto);
            return super.ok(BaseApiDto.newBaseApiDto());
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "회원가입 실패 : " + e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<BaseApiDto<?>> login(@RequestBody UserDto userDto) {
        try {
             LoginResponseDto responseDto = userService.login(userDto);
            return super.ok(new BaseApiDto<>(responseDto));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "로그인 실패 : " + e.getMessage());
        }

    }
}
