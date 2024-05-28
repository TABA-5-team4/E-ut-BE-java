package taba.team4.eut.biz.user.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import taba.team4.eut.biz.user.dto.CustomUserDetails;
import taba.team4.eut.biz.user.dto.LoginResponseDto;
import taba.team4.eut.biz.user.dto.UserDto;
import taba.team4.eut.biz.user.entity.RefreshEntity;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.biz.user.repository.RefreshRepository;
import taba.team4.eut.biz.user.repository.UserRepository;
import taba.team4.eut.jwt.JWTUtil;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationConfiguration configuration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public void join(UserDto userDto) {
        String phone = userDto.getPhone();


        Boolean isExist = userRepository.existsByPhone(phone);
        if (isExist) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        UserEntity user = new UserEntity();

         user.setPhone(phone);
         user.setPassword(bCryptPasswordEncoder.encode(phone));
         user.setMemberType('P');
         user.setRole("ROLE_USER");

         userRepository.save(user);
    }

    public LoginResponseDto login(UserDto userDto) throws Exception {
        String phone = userDto.getPhone();

        UserEntity user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(phone, phone, null);
        Authentication authentication = configuration.getAuthenticationManager().authenticate(authToken);
        if (!authentication.isAuthenticated()) {
            throw new IllegalArgumentException("로그인 실패");
        }

        // UserDetails 클래스에서 유저(principal)를 가져옴 + 타입 캐스트
        UserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        // role 값을 이터레이터로 추출
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority authority = iterator.next();

        String role = authority.getAuthority();

        // jwt 생성
//        String token = jwtUtil.createJwt(username, role, 60*60*10L);
        //토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 86400000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //Refresh 토큰 저장
        addRefreshEntity(username, refresh);


        return new LoginResponseDto(user, access, refresh);
    }
    private void addRefreshEntity(String username, String refresh) {

        Date date = new Date(System.currentTimeMillis() + 86400000);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
