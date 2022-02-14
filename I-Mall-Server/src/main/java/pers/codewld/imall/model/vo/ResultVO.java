package pers.codewld.imall.model.vo;

import lombok.Getter;
import pers.codewld.imall.model.enums.ResultCode;

/**
 * <p>
 * 响应体
 * </p>
 *
 * @author codewld
 * @since 2022-02-12
 */
@Getter
public class ResultVO {

    /**
     * 状态码
     */
    private final int code;

    /**
     * 描述
     */
    private final String msg;

    /**
     * 数据
     */
    private final Object data;

    public ResultVO(Object data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }
}
