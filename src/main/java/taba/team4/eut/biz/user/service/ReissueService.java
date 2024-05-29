package taba.team4.eut.biz.user.service;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import taba.team4.eut.biz.user.dto.LoginResponseDto;
import taba.team4.eut.biz.user.dto.TokenDto;
import taba.team4.eut.biz.user.dto.UserDto;
import taba.team4.eut.biz.user.entity.RefreshEntity;
import taba.team4.eut.biz.user.repository.RefreshRepository;
import taba.team4.eut.jwt.JWTUtil;

import java.util.Date;

@Service
public class ReissueService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public ReissueService(JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    public TokenDto reissue(TokenDto tokenDto) {
        String refresh = tokenDto.getRefreshToken();
        // 토큰 존재 확인
        if (refresh == null || refresh.equals("")) {
            throw new IllegalArgumentException("리프레시 토큰이 없습니다.");
        }
        // 토큰 만료 확인
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("리프레시 토큰이 만료되었습니다.");
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, thirtyDaysInMillis);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, thirtyDaysInMillis);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, thirtyDaysInMillis);

        TokenDto dto = new TokenDto();
        dto.setAccessToken(newAccess);
        dto.setRefreshToken(newRefresh);
        return dto;
    }
    private static long thirtyDaysInMillis = 30L * 24 * 60 * 60 * 1000; // 30일을 밀리초로 변환
    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
