package pers.codewld.imall.security.controller.advice;

import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.codewld.imall.common.model.enums.ResultCode;
import pers.codewld.imall.common.model.vo.ResultVO;

/**
 * <p>
 * 安全异常处理类
 * </p>
 *
 * @author codewld
 * @since 2022-03-14
 */
@Order(1) // 配置使该类提前加载，避免被ExceptionAdvice类中的通用异常处理所拦截
@RestControllerAdvice
public class SecurityExceptionAdvice {

    /**
     * 登录异常处理
     */
    @ExceptionHandler({BadCredentialsException.class, DisabledException.class})
    public ResultVO badCredentialsException(Exception e) {
        return ResultVO.fail(e.getMessage());
    }

    /**
     * 未授权异常
     */
    @ExceptionHandler({AccessDeniedException.class})
    public ResultVO accessDeniedException(Exception e) {
        return ResultVO.error(ResultCode.FORBIDDEN);
    }
}
