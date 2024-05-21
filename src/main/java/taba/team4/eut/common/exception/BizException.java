package taba.team4.eut.common.exception;

import taba.team4.eut.common.code.ResultCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class BizException extends RuntimeException {

    private final String HTTP_STATUS_401 = "401 UNAUTHORIZED";
    private final String HTTP_STATUS_403 = "403 FORBIDDEN";
    private final String HTTP_STATUS_404 = "404 NOT_FOUND";
    private ResultCode resultCode;

    public BizException(String message) {
        super(message);

        if (HTTP_STATUS_401.equals(message)) {
            this.resultCode = ResultCode.HTTP_401;
        }
        else if (HTTP_STATUS_403.equals(message)) {
            this.resultCode = ResultCode.HTTP_403;
        }
        else if (HTTP_STATUS_404.equals(message)) {
            this.resultCode = ResultCode.HTTP_404;
        }
        else {
            this.resultCode = ResultCode.FAIL.setErrMsg(message);
        }
    }

    public BizException(ResultCode resultCode) {
        super(resultCode.getRtMsgKo());
        this.resultCode = resultCode;
    }

    /**
     * ResultCode default 에러 메시지 + errMsg
     */
    public BizException(ResultCode resultCode, String errMsg) {
        super(resultCode.getRtMsgEn());
        if (StringUtils.isNotBlank(errMsg)) {
            resultCode.appendErrMsg(errMsg);
        }
        this.resultCode = resultCode;
    }

    public BizException(Throwable cause) {
        super(cause);
    }

}
