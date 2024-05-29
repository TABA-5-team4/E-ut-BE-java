package taba.team4.eut.biz.user.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taba.team4.eut.biz.user.dto.LoginResponseDto;
import taba.team4.eut.biz.user.dto.TokenDto;
import taba.team4.eut.biz.user.dto.UserDto;
import taba.team4.eut.biz.user.entity.RefreshEntity;
import taba.team4.eut.biz.user.repository.RefreshRepository;
import taba.team4.eut.biz.user.service.ReissueService;
import taba.team4.eut.common.controller.BaseApiController;
import taba.team4.eut.common.controller.BaseApiDto;
import taba.team4.eut.jwt.JWTUtil;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class ReissueController extends BaseApiController<BaseApiDto<?>> {
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final ReissueService reissueService;

    public ReissueController(JWTUtil jwtUtil, RefreshRepository refreshRepository, ReissueService reissueService) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.reissueService = reissueService;
    }

    @PostMapping("/reissue")
    public ResponseEntity<BaseApiDto<?>> reissue(@RequestBody TokenDto tokenDto) {
        try {
            TokenDto responseDto = reissueService.reissue(tokenDto);
            return super.ok(new BaseApiDto<>(responseDto));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "재발급 실패 : " + e.getMessage());
        }

    }

    @PostMapping("/reissue/cookie")
    public ResponseEntity<?> reissueByCookie(HttpServletRequest request, HttpServletResponse response) {
        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {

            //response body
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60); // 쿠키 생명 주기
//        cookie.setSecure(true); // https에서만 쿠키 전송
//        cookie.setPath("/"); // 모든 경로에서 쿠키 전송
        cookie.setHttpOnly(true); // 자바스크립트에서 쿠키 접근 불가
        return cookie;
    }
}
