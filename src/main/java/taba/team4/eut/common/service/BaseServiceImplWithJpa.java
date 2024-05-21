package taba.team4.eut.common.service;

import taba.team4.eut.common.code.ResultCode;
import taba.team4.eut.common.dao.BaseRepository;
import taba.team4.eut.common.dto.BaseEntity;
import taba.team4.eut.common.dto.BaseModel;
import taba.team4.eut.common.dto.PageEntity;
import taba.team4.eut.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
public abstract class BaseServiceImplWithJpa<
            Model extends BaseModel,
            Entity extends BaseEntity,
            PkType,
            Repository extends BaseRepository<Entity, PkType>> implements BaseService<Model, PkType> {

    protected Repository repository;

    private final int DEFAULT_PAGE_PER_CNT = 10;

    protected Pageable toPageable(PageEntity pageEntity) {
        return PageRequest.of(pageEntity.getPageNo()-1, pageEntity.getPagePerCnt());
    }
    protected Pageable toPageable(int pageNo) {
        return this.toPageable(pageNo, DEFAULT_PAGE_PER_CNT); // 기본 10개
    }
    protected Pageable toPageable(int pageNo, int pagePerCnt) {
        return PageRequest.of(pageNo-1, pagePerCnt);
    }


    @Override
    public long getTotalCnt(PageEntity<Model> pageEntity) throws Exception {
        Page<Entity> page = repository.findAll(toPageable(pageEntity));
        return page.getTotalElements();
    }

    @Override
    public PageEntity<Model> getList(PageEntity<Model> pageEntity) throws Exception {

        Page<Entity> page = repository.findAll(toPageable(pageEntity));

        Stream<Model> stream = page.getContent().stream()
                .map(entity -> entity.toModel());

        pageEntity.setTotalCnt(page.getTotalElements());
        pageEntity.setDtoList(stream.toList());
        return pageEntity;
    }

    @Override
    public List<Model> getList(int pagePerCnt) throws Exception {
        Page<Entity> page = repository.findAll(toPageable(1, pagePerCnt));

        Stream<Model> stream = page.getContent().stream()
                .map(entity -> entity.toModel());
        return stream.toList();
    }

    @Override
    public Model getDetail(PkType pk) throws Exception {
        return repository.findById(pk)
                .orElseThrow(() -> new BizException(ResultCode.DATA_NOT_FOUND)).toModel();
    }

    @Override
    public Model add(Model model) throws Exception {
        return repository.save(model.toEntity()).toModel();
    }

    @Override
    public Model modify(Model model) throws Exception {
        return repository.save(model.toEntity()).toModel();
    }

    @Override
    public void remove(PkType pk) throws Exception {
        repository.findById(pk)
                .orElseThrow(() -> new BizException(ResultCode.DATA_NOT_FOUND));
        repository.deleteById(pk);
    }
}
