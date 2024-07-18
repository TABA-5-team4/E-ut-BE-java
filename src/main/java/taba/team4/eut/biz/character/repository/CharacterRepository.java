package taba.team4.eut.biz.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taba.team4.eut.biz.character.entity.CharacterEntity;
import taba.team4.eut.biz.user.entity.UserEntity;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    List<CharacterEntity> findByUser(UserEntity user);
}
