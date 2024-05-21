package taba.team4.eut.common.utils;

import taba.team4.eut.common.code.ResultCode;
import taba.team4.eut.common.dto.BaseEntity;
import taba.team4.eut.common.dto.BaseModel;
import taba.team4.eut.common.dto.BaseResponseModel;
import taba.team4.eut.common.dto.PageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Slf4j
public class ResponseEntityUtil<T> {
    //----------------------------------------------------------------
    // 성공
    //----------------------------------------------------------------
    public static ResponseEntity ok() {
        return getResult(ResultCode.SUCCESS);
    }
    public static ResponseEntity ok(String result) {
        return getResultByString(ResultCode.SUCCESS, result);
    }
    public static ResponseEntity ok(BaseModel result) {
        return getResultByModel(ResultCode.SUCCESS, result);
    }
    public static ResponseEntity ok(List<?> resultList) {
        return getResultByList(ResultCode.SUCCESS, resultList);
    }
    public static ResponseEntity ok(BaseResponseModel<?> entity) {
        return ResponseEntity.ok().body(entity);
    }


    /**
     * JPA 사용 시 결과 값에 페이징 정보 포함하여 리턴
     * org.springframework.data.domain.Page
     * @param page
     * @return
     */
    public static ResponseEntity ok(Page<?> page) {
        return getResultByPage(ResultCode.SUCCESS, page);
    }

    /**
     * Mysql Mapper 사용 시 결과 값에 페이징 정보 포함하여 리턴
     * io.namp.eco.framework.common.dto.PageEntity
     * @param pageEntity
     * @return
     */
    public static ResponseEntity ok(PageEntity<?> pageEntity) {
        return getResultByPageEntity(ResultCode.SUCCESS, pageEntity);
    }

    public static ResponseEntity ok(Map<String, Object> resultMap) {
        return getResultByMap(ResultCode.SUCCESS, resultMap);
    }

    //----------------------------------------------------------------
    // 실패
    //----------------------------------------------------------------
    public static ResponseEntity fail() {
        return getResult(ResultCode.FAIL);
    }
    public static ResponseEntity fail(ResultCode resultCode) {
        return getResult(resultCode);
    }
    public static ResponseEntity fail(List<String> errors) {
        //return ResponseEntity.ok(new ErrorResponse(ResultCode.FAIL.getRtCode(), "Validation failure", errors));
        return getResultByList(ResultCode.FAIL, errors);
    }
    public static ResponseEntity fail(ResultCode resultCode, Map<String, Object> resultMap) {
        return getResultByMap(resultCode, resultMap);
    }


    //----------------------------------------------------------------
    // 권한오류
    //----------------------------------------------------------------



    //----------------------------------------------------------------
    // 파라메터 오류 - invalidParam
    //----------------------------------------------------------------
    public static ResponseEntity invalidParam(List<String> errors) {
        //return ResponseEntity.ok(new ErrorResponse(ResultCode.FAIL.getRtCode(), "Validation failure", errors));
        return getResultByList(ResultCode.INVALID_PARAM, errors);
    }


    //----------------------------------------------------------------
    // 데이터 없음 - notFound
    //----------------------------------------------------------------


    //----------------------------------------------------------------
    // 에러코드 직접 정의
    //----------------------------------------------------------------
    public static ResponseEntity getResult(ResultCode resultCode) {
        return getResultByModel(resultCode, null);
    }
    public static ResponseEntity getResultByString(ResultCode resultCode, String result) {
        BaseResponseModel<BaseEntity> entity = new BaseResponseModel(resultCode, result);
        return ResponseEntity.ok().body(entity);
    }
    public static ResponseEntity getResultByModel(ResultCode resultCode, BaseModel result) {
        BaseResponseModel<BaseEntity> entity = new BaseResponseModel(resultCode, result);
        return ResponseEntity.ok().body(entity);
    }
    public static ResponseEntity getResultByList(ResultCode resultCode, List list) {
        BaseResponseModel<List> entity = new BaseResponseModel(resultCode, list);

        return ResponseEntity.ok().body(entity);
    }


    public static ResponseEntity getResultByPage(ResultCode resultCode, Page<?> page) {
        BaseResponseModel<List> entity = new BaseResponseModel(resultCode, page.getContent(), page);
        return ResponseEntity.ok().body(entity);
    }

    public static ResponseEntity getResultByPageEntity(ResultCode resultCode, PageEntity<?> pageEntity) {
        BaseResponseModel<List> entity = new BaseResponseModel(resultCode, pageEntity);
        return ResponseEntity.ok().body(entity);
    }


    public static ResponseEntity getResultByMap(ResultCode resultCode, Map<String, Object> resultMap) {
        BaseResponseModel<Map<String, Object>> entity = new BaseResponseModel(resultCode, resultMap);

        return ResponseEntity.ok().body(entity);
    }


}
