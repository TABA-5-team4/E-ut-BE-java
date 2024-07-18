package taba.team4.eut.biz.character.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taba.team4.eut.biz.character.dto.CharacterRequestDTO;
import taba.team4.eut.biz.character.service.CharacterService;
import taba.team4.eut.common.controller.BaseApiController;
import taba.team4.eut.common.controller.BaseApiDto;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/character")
@RequiredArgsConstructor
public class CharacterController extends BaseApiController<BaseApiDto<?>> {

    private final CharacterService characterService;

    @GetMapping("")
    public ResponseEntity<BaseApiDto<?>> getCharacters() {
        try {
            log.info("캐릭터 목록 조회 요청");
            return super.ok(new BaseApiDto<>(characterService.getCharacters()));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "캐릭터 목록 조회 실패 : " + e.getMessage());
        }
    }

    @PostMapping(path = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseApiDto<?>> createCharacter(@ModelAttribute CharacterRequestDTO dto) {
        try {
            log.info("캐릭터 생성 요청 : {}", dto);
            return super.ok(new BaseApiDto<>(characterService.createCharacter(dto)));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "캐릭터 생성 실패 : " + e.getMessage());
        }
    }

}
