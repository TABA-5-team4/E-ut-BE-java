package taba.team4.eut.biz.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taba.team4.eut.biz.user.entity.UserEntity;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByPhone(String phone);

    Boolean existsByPhone(String phone);

//    Optional<User> findUserById(Long id);
}
