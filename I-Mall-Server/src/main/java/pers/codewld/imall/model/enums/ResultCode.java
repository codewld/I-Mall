package pers.codewld.imall.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 状态码及描述 枚举类
 * </p>
 *
 * @author codewld
 * @since 2022-02-12
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(0, "操作成功"),

    FAILED(1000, "操作失败"),

    VALIDATE_FAILED(2000, "参数错误"),

    UNAUTHORIZED(9100, "未登录"),

    FORBIDDEN(9200, "未授权");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 描述
     */
    private final String msg;
}
