package taba.team4.eut.biz.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import taba.team4.eut.biz.user.dto.CustomUserDetails;
import taba.team4.eut.biz.user.entity.UserEntity;
import taba.team4.eut.biz.user.repository.UserRepository;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByPhone(username);

        if (user != null) {
            return new CustomUserDetails(user);
        }

        return null;
    }
}
