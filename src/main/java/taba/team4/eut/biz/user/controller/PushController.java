package taba.team4.eut.biz.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taba.team4.eut.biz.user.dto.FcmDTO;
import taba.team4.eut.biz.user.dto.PushDTO;
import taba.team4.eut.biz.user.service.UserService;
import taba.team4.eut.common.controller.BaseApiController;
import taba.team4.eut.common.controller.BaseApiDto;

@Slf4j
@RestController
@RequestMapping("/api/v1/push")
public class PushController extends BaseApiController<BaseApiDto<?>> {

    private final UserService userService;

    public PushController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseApiDto<?>> register(@RequestBody FcmDTO dto) {
        try {
            log.info("register : {}", dto.getFcmToken());
            userService.registerToken(dto.getFcmToken());
            return super.ok(BaseApiDto.newBaseApiDto());
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "푸시 토큰 등록 실패 : " + e.getMessage());
        }
    }

    @PostMapping("/send/{id}")
    public ResponseEntity<BaseApiDto<?>> send(@RequestBody PushDTO dto, @PathVariable Long id) {
        try {
            log.info("title : {}", dto.getTitle());
            log.info("message : {}", dto.getMessage());
            log.info("id : {}", id);
            userService.sendPush(id, dto);
            return super.ok(BaseApiDto.newBaseApiDto());
        } catch (Exception e) {
            e.printStackTrace();
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "푸시 전송 실패 : " + e.getMessage());
        }
    }
}
