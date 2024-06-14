package taba.team4.eut.biz.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import taba.team4.eut.biz.user.dto.*;
import taba.team4.eut.biz.user.entity.ParentChildMappingEntity;
import taba.team4.eut.biz.user.entity.RefreshEntity;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.biz.user.repository.ParentChildMappingRepository;
import taba.team4.eut.biz.user.repository.RefreshRepository;
import taba.team4.eut.biz.user.repository.UserRepository;
import taba.team4.eut.common.security.SecurityUtil;
import taba.team4.eut.jwt.JWTUtil;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationConfiguration configuration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final ParentChildMappingRepository parentChildMappingRepository;
    private final WebClient.Builder webClientBuilder;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public void join(UserDto userDto) {
        String phone = userDto.getPhone();

        // 1. 유저 중복 확인
        Boolean isExist = userRepository.existsByPhone(phone);
        if (isExist) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
//            throw new BizException(ResultCode.ALREADY_EXISTS, "이미 존재하는 유저입니다.");
        }

        // 2. 유저 타입에 따라 회원가입
        if (userDto.getType() == null) {
            throw new IllegalArgumentException("유저 타입이 없습니다.");
        } else if (userDto.getType().equals("P")) {
            // 부모 회원가입
            UserEntity user = new UserEntity();

            user.setPhone(phone);
            user.setPassword(bCryptPasswordEncoder.encode(phone));
            user.setMemberType('P');
            user.setRole("ROLE_PARENT");

            userRepository.save(user);
        } else if (userDto.getType().equals("C")) {
            // 자녀 회원가입

            // 부모 확인
            Optional<UserEntity> parent = userRepository.findByPhone(userDto.getParentPhone());
            if (parent.isEmpty() || parent.get().getMemberType() != 'P') {
                throw new IllegalArgumentException("부모 정보가 존재하지 않습니다.");
            }

            // 자녀 저장
            UserEntity user = new UserEntity();
            user.setPhone(phone);
            user.setPassword(bCryptPasswordEncoder.encode(phone));
            user.setMemberType('C');
            user.setEmail(userDto.getEmail());
            user.setRole("ROLE_CHILD");
            userRepository.save(user);

            // 부모-자녀 매핑
            ParentChildMappingEntity mapping = new ParentChildMappingEntity(parent.get(), user);
            parentChildMappingRepository.save(mapping);

        } else {
            throw new IllegalArgumentException("유저 타입이 잘못되었습니다. P or C");
        }


    }

    public LoginResponseDto login(UserDto userDto) throws Exception {
        String phone = userDto.getPhone();

        Optional<UserEntity> user = userRepository.findByPhone(phone);
        if (user.isEmpty()) {
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
        String access = jwtUtil.createJwt("access", username, role, thirtyDaysInMillis);
        String refresh = jwtUtil.createJwt("refresh", username, role, thirtyDaysInMillis);

        //Refresh 토큰 저장
        addRefreshEntity(username, refresh);


        return new LoginResponseDto(user.get(), access, refresh);
    }
    private static long thirtyDaysInMillis = 30L * 24 * 60 * 60 * 1000; // 30일을 밀리초로 변환
    private void addRefreshEntity(String username, String refresh) {

        Date date = new Date(System.currentTimeMillis() + thirtyDaysInMillis);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    public void logout(UserDto userDto) {

    }

    public void registerToken(String token) {

        // 사용자 정보 조회
        Optional<UserEntity> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhone);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }
        UserEntity userEntity = user.get();
        userEntity.setFcmToken(token);
        // 푸시 토큰 저장
        userRepository.save(userEntity);
    }

    public void sendPush(Long id, PushDTO dto) throws IOException {
        // 사용자 정보 조회
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }
        String token = user.get().getFcmToken();
        if (token == null) {
            throw new IllegalArgumentException("푸시 토큰이 없습니다.");
        }
        log.info("token : {}", token);
        FcmMessageDto message = makeMessage(dto, token);

        // 푸시 전송
        WebClient webClient = webClientBuilder.build();
        String response =webClient.post()
                .uri("https://fcm.googleapis.com/v1/projects/taba-app-proj/messages:send")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getAccessToken())
                .body(BodyInserters.fromValue(message))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(response);

    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/taba-app-proj-firebase-adminsdk-qjj32-6341da6b21.json";

        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private FcmMessageDto makeMessage(PushDTO fcmSendDto, String token) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
//        FcmMessageDto fcmMessageDto =
                return FcmMessageDto.builder()
                .message(FcmMessageDto.Message.builder()
                        .token(token)
                        .notification(FcmMessageDto.Notification.builder()
                                .title(fcmSendDto.getTitle())
                                .body(fcmSendDto.getMessage())

                                .build()
                        ).build()).build();

//        return om.writeValueAsString(fcmMessageDto);
    }

}
