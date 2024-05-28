package taba.team4.eut.biz.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taba.team4.eut.biz.user.entity.ParentChildMappingEntity;
import taba.team4.eut.biz.user.entity.ParentChildMappingId;

@Repository
public interface ParentChildMappingRepository extends JpaRepository<ParentChildMappingEntity, ParentChildMappingId> {
}
