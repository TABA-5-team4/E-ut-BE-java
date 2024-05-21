package taba.team4.eut.biz.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taba.team4.eut.biz.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
//    Optional<User> findUserById(Long id);
}
