package taba.team4.eut.biz.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taba.team4.eut.biz.chat.dto.ChatRequestDto;
import taba.team4.eut.biz.chat.dto.SttChatResponseDto;
import taba.team4.eut.biz.chat.service.ChatService;
import taba.team4.eut.common.controller.BaseApiController;
import taba.team4.eut.common.controller.BaseApiDto;

@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController extends BaseApiController<BaseApiDto<?>> {

    private final ChatService chatService;


    @GetMapping("")
    public String chatP() {
        return "chat";
    }

    @PostMapping(path = "/stt", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseApiDto<?>> chatWithStt(@ModelAttribute ChatRequestDto chatRequestDto) {
        try {
            log.info("stt 요청 : {}", chatRequestDto);
            SttChatResponseDto stt = chatService.stt(chatRequestDto);

            return super.ok(new BaseApiDto<>(stt));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "음성인식 실패 : " + e.getMessage());
        }
    }

}
