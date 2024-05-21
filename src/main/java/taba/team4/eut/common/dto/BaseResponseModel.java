package taba.team4.eut.common.dto;

import taba.team4.eut.common.code.ResultCode;
import taba.team4.eut.common.exception.BizException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseModel<T> {
    protected String message;
    protected String code;
    protected T result;
    @JsonProperty("paging")
    protected Paging paging;

    /**
     * List<Map<String, Object>> result 를 List<O> result 로 타입 변경
     *
     * result 가 List 가 아니면 BizException을 리턴한다.
     */
    public <O> BaseResponseModel<List<O>> convertResult(Class<O> toValueType) {
        if (! (this.result instanceof List) ) {
            throw new BizException(ResultCode.NOT_SUPPORTED, " # because of result is not List type");
        }

        ObjectMapper mapper = new ObjectMapper();
        List<O> objectList = new ArrayList<>();

        List<Object> list = (List<Object>) result;

        for (Object fromValue :list) {
            O object = mapper.convertValue(fromValue, toValueType);
            objectList.add(object);
        }
        this.result = (T) objectList;

        BaseResponseModel<List<O>> newResModel = new BaseResponseModel<>();
        newResModel.setCode(this.code);
        newResModel.setMessage(this.message);
        newResModel.setPaging(this.paging);
        newResModel.setResult(objectList);

        return newResModel;
    }

    @Getter
    @Setter
    @ToString
    public static class Paging {
        int pagePerCnt, totalPages, pageNo;
        long totalCnt;
        boolean hasNext;
        private Paging() {}
        public Paging(Page p) {
            this.pagePerCnt = p.getSize();
            this.totalCnt = p.getTotalElements();
            this.totalPages = p.getTotalPages();
            this.pageNo = p.getNumber() + 1;
            this.hasNext = p.hasNext();
        }
        public Paging(PageEntity p) {
            this.pagePerCnt = p.getPagePerCnt();
            this.totalCnt = p.getTotalCnt();
            this.totalPages = p.getLastPageNo();
            this.pageNo = p.getPageNo();
            this.hasNext = p.hasMore();
        }
    }

    public BaseResponseModel() {
        //
    }

    public BaseResponseModel(ResultCode resultCode, T result) {
        log.debug("result = {}", result);
        this.code = resultCode.getRtCode();
        this.message = resultCode.getRtMsgEn();
        this.result = result;
    }

    /**
     * 페이징 정보 포함
     * @param resultCode
     * @param result
     * @param p
     */
    public BaseResponseModel(ResultCode resultCode, T result, Page p) {
        log.debug("result = {}", result);
        this.code = resultCode.getRtCode();
        this.message = resultCode.getRtMsgEn();
        this.result = result;
        this.paging = new Paging(p);
    }

    public BaseResponseModel(ResultCode resultCode, PageEntity pageEntity) {
        log.debug("result = {}", pageEntity.getDtoList());
        this.code = resultCode.getRtCode();
        this.message = resultCode.getRtMsgEn();
        this.result = (T) pageEntity.getDtoList();
        this.paging = new Paging(pageEntity);
    }

}