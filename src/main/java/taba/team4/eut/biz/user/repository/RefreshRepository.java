package taba.team4.eut.biz.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import taba.team4.eut.biz.user.entity.RefreshEntity;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {
    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
