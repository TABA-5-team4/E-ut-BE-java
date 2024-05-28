package taba.team4.eut.biz.user.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taba.team4.eut.biz.user.dto.UserDto;
import taba.team4.eut.biz.user.service.JoinService;
import taba.team4.eut.common.controller.BaseApiController;
import taba.team4.eut.common.controller.BaseApiDto;

@RestController
@RequestMapping("/api/v1")
public class UserController extends BaseApiController<BaseApiDto<?>> {

    private final JoinService joinService;

    public UserController(JoinService joinService) {
        this.joinService = joinService;
    }


    @PostMapping("/join")
    public ResponseEntity<BaseApiDto<?>> join(@RequestBody UserDto userDto) {
        try {
            System.out.println("userDto.getPhone() = " + userDto.getPhone() + "userDto.getEmail() = " + userDto.getEmail() + "userDto.getParentPhone() = " + userDto.getParentPhone());
            joinService.join(userDto);
            return super.ok(BaseApiDto.newBaseApiDto());
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "회원가입 실패 : " + e.getMessage());
        }

    }
}
