package pers.codewld.imall.common.exception;

import pers.codewld.imall.common.model.enums.ResultCode;

/**
 * <p>
 * 自定义异常
 * </p>
 *
 * @author codewld
 * @since 2022-02-23
 */
public class CustomException extends RuntimeException {

    private final ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public CustomException(String msg) {
        super(msg);
        this.resultCode = ResultCode.FAILED;
    }

    public CustomException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
