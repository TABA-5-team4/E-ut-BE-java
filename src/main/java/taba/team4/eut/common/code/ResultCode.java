package taba.team4.eut.common.code;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS         ("0000", "성공", "success"),

    HTTP_401("401", "인증 오류", "unauthorized"),
    HTTP_403("403", "권한 없음", "forbidden"),
    HTTP_404("404", "잘못된 URL 경로", "invalid url"),

    DATA_NOT_FOUND  ("4001", "조회 데이터 없음", "data not found"),
    INVALID_PARAM   ("4100", "파라메터 누락", "invalid parameter"),
    INVALID_VALUE   ("4101", "잘못된 값", "invalid value"),
    ALREADY_EXISTS  ("4101", "등록 실패. 이미 등록되이 있는 데이터.", "registration failed. data already registered"),
    NOT_SUPPORTED   ("8000", "제공되지 않는 기능", "not supported function"),
    UNKNOWN_ERROR   ("9000", "알수 없는 오류.", "unknown error"),
    FAIL            ("9999", "실패", "fail"),
    UNDEFINED_CODE  ("", "정의 되지 않은 코드 값", "undefined code"),
    ;

    private String rtCode;
    private String rtMsgKo;
    private String rtMsgEn;

    ResultCode(String rtCode, String rtMsgKo, String rtMsgEn) {
        this.rtCode = rtCode;
        this.rtMsgKo = rtMsgKo;
        this.rtMsgEn = rtMsgEn;
    }

    public ResultCode setErrMsg(String errMsg) {
        this.rtMsgKo = errMsg;
        this.rtMsgEn = errMsg;
        return this;
    }

    public ResultCode appendErrMsg(String errMsg) {
        this.rtMsgKo = this.rtMsgKo + " : " + errMsg;
        this.rtMsgEn = this.rtMsgEn + " : " + errMsg;
        return this;
    }

    /**
     * rtCode 값으로 ResultCode 확인
     */
    public static ResultCode getByCode(String rtCode) {
        for (ResultCode resultCode:ResultCode.values()) {
            if (resultCode.getRtCode().equals(rtCode)) {
                return resultCode;
            }
        }
        return ResultCode.UNDEFINED_CODE;
    }


}
