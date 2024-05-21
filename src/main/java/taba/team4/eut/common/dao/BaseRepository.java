package taba.team4.eut.common.dao;

import taba.team4.eut.common.dto.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * CrudRepository
 *  - 일반적인 CURD를 제공
 *
 * PagingAndSortingRepository
 *  - CrudRepository를 상속 받음. 페이징 소팅 기능이 필요할 때 사용
 *
 *
 * JpaRepository
 *  - PagingAndSortingRepository를 상속 받음. Jpa 특성 기능이 필요할 때 사용
 *  - Jpa 특성을 파악하기 전에는 사용하지 않기
 *
 *  interface ApplicationRepository<T> extends PagingAndSortingRepository<T, Long> { }
 */
@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity, PkType> extends JpaRepository<Entity, PkType> {
    /**
     * delete 기능은 제공하지 않음.
     * @param entity
     */
//    @Override
//    void delete(Entity entity);
}
