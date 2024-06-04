package taba.team4.eut.biz.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taba.team4.eut.biz.user.entity.ParentChildMappingEntity;
import taba.team4.eut.biz.user.entity.ParentChildMappingId;
import taba.team4.eut.biz.user.entity.UserEntity;

import java.util.Optional;

@Repository
public interface ParentChildMappingRepository extends JpaRepository<ParentChildMappingEntity, ParentChildMappingId> {

    Optional<ParentChildMappingEntity> findByChild(UserEntity child);
}
