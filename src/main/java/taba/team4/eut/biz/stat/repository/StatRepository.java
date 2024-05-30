package taba.team4.eut.biz.stat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taba.team4.eut.biz.stat.entity.StatEntity;

@Repository
public interface StatRepository extends JpaRepository<StatEntity, Long> {
}
