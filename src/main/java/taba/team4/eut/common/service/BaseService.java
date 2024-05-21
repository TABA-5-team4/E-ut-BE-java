package taba.team4.eut.common.service;



import taba.team4.eut.common.dto.BaseModel;
import taba.team4.eut.common.dto.PageEntity;

import java.util.List;

public interface BaseService<Dto extends BaseModel, PkType> {

    /**
     * 조회 조건에 맞는 로우수 리턴
     * @param pageEntity 검색 조건
     */
    long getTotalCnt(PageEntity<Dto> pageEntity) throws Exception;

    /**
     * 페이징 있는 목록 조회
     * @param pageEntity 페이징 및 검색 조건 포함
     */
    PageEntity<Dto> getList(PageEntity<Dto> pageEntity) throws Exception;

    /**
     * 페이징 없는 목록 조회
     * @param pagePerCnt 최대 조회 갯수
     */
    List<Dto> getList(int pagePerCnt) throws Exception;

    /**
     * 상세 리턴
     */
    Dto getDetail(PkType pk) throws Exception;

    /**
     * 저장
     */
    Dto add(Dto dto) throws Exception;

    /**
     * 수정
     */
    Dto modify(Dto dto) throws Exception;

    /**
     * 삭제
     */
    void remove(PkType pk) throws Exception;
}
