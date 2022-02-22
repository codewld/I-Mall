package pers.codewld.imall.controller.advice;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.codewld.imall.model.enums.ResultCode;
import pers.codewld.imall.model.vo.ResultVO;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
     * 账号相关异常处理
     */
    @ExceptionHandler({BadCredentialsException.class, DisabledException.class})
    public ResultVO badCredentialsException(Exception e) {
        return ResultVO.fail(e.getMessage());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class})
    public ResultVO validateFailedException(Exception e) {
        String msg = null;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            msg = ((ConstraintViolation<?>) (ex.getConstraintViolations().toArray()[0])).getMessage();
        }
        return ResultVO.error(ResultCode.VALIDATE_FAILED, msg);
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
