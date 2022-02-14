package pers.codewld.imall.controller.advice;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.codewld.imall.model.vo.ResultVO;

/**
 * <p>
 * 全局异常处理类
 * </p>
 *
 * @author codewld
 * @since 2022-02-14
 */
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 登录失败异常处理
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResultVO badCredentialsException(Exception e) {
        e.printStackTrace();
        return ResultVO.fail(e.getMessage());
    }

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    public ResultVO exception(Exception e) {
        e.printStackTrace();
        return ResultVO.fail();
    }

}
