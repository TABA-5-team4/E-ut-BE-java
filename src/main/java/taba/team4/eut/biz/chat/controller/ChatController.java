package taba.team4.eut.biz.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taba.team4.eut.common.controller.BaseApiController;
import taba.team4.eut.common.controller.BaseApiDto;

@RestController
@RequestMapping("/api/v1")
public class ChatController extends BaseApiController<BaseApiDto<?>> {

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }

}
