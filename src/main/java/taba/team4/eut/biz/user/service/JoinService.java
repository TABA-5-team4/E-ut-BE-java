package taba.team4.eut.biz.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import taba.team4.eut.biz.user.dto.UserDto;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.biz.user.repository.UserRepository;

@Service
@AllArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
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
}
