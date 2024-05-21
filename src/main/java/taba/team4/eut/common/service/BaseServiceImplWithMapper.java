package taba.team4.eut.common.service;

import taba.team4.eut.common.code.ResultCode;
import taba.team4.eut.common.dao.BaseMapper;
import taba.team4.eut.common.dto.BaseEntity;
import taba.team4.eut.common.dto.BaseModel;
import taba.team4.eut.common.dto.PageEntity;
import taba.team4.eut.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
public abstract class BaseServiceImplWithMapper<
        Model extends BaseModel,
        Entity extends BaseEntity,
        PkType,
        Mapper extends BaseMapper<Entity, PkType>> implements BaseService<Model, PkType> {

    // 상속받는 클래스 생성자에서 정의
    protected Mapper mapper;

    protected PageEntity<Entity> toEntity(PageEntity<Model> param) {
        PageEntity<Entity> pageEntity = new PageEntity<>();
        pageEntity.setPageNo(param.getPageNo());
        pageEntity.setPagePerCnt(param.getPagePerCnt());
        pageEntity.setPageGroupPerCnt(param.getPageGroupPerCnt());
        pageEntity.setSearchMap(param.getSearchMap());

        if (param.getSearchDto() != null) {
            pageEntity.setSearchDto(param.getSearchDto().toEntity());
        }

        return pageEntity;
    }

    @Override
    public long getTotalCnt(PageEntity<Model> param) throws Exception {
        return mapper.totalCnt(this.toEntity(param));
    }

    @Override
    public PageEntity<Model> getList(PageEntity<Model> param) throws Exception {

        PageEntity<Entity> pageEntity = this.toEntity(param);

        long totalCnt = mapper.totalCnt(pageEntity);
        if (totalCnt > 0) {
            List<Entity> entityList = mapper.selectList(pageEntity);
            Stream<Model> stream = entityList.stream().map(e -> e.toModel());

            param.setTotalCnt(totalCnt);
            param.setDtoList(stream.toList());
        }

        return param;
    }

    @Override
    public List<Model> getList(int pagePerCnt) throws Exception {
        PageEntity<Entity> pageEntity = new PageEntity<>(pagePerCnt);

        List<Entity> entityList = mapper.selectList(pageEntity);
        Stream<Model> stream = entityList.stream().map(e -> e.toModel());

        return stream.toList();
    }


    @Override
    public Model getDetail(PkType pk) throws Exception {
        Entity entity = mapper.selectDetail(pk);
        if (entity == null) {
            throw new BizException(ResultCode.DATA_NOT_FOUND);
        }
        return entity.toModel();
    }

    @Override
    public Model add(Model dto) throws Exception {
        Entity entity = dto.toEntity();
        mapper.insert(entity);
        return entity.toModel();
    }

    @Override
    public Model modify(Model dto) throws Exception {
        Entity entity = dto.toEntity();
        mapper.update(entity);
        return entity.toModel();
    }

    @Override
    public void remove(PkType pk) throws Exception {
        mapper.delete(pk);
    }

}
