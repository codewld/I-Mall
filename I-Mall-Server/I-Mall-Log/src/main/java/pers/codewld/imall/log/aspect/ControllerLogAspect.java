package pers.codewld.imall.log.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.codewld.imall.log.annotation.DisableLogDBStorage;
import pers.codewld.imall.log.model.entity.Log;
import pers.codewld.imall.log.service.LogService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 接口日志 切面类
 * </p>
 *
 * @author codewld
 * @since 2022-03-15
 */
@Aspect
@Component
public class ControllerLogAspect {

    @Autowired
    LogService logService;

    @Autowired
    ObjectMapper objectMapper;

    @Pointcut("execution(* pers.codewld.imall.*.controller.*.*(..))")
    public void controllerLog() {
    }

    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Class<?> aClass = joinPoint.getTarget().getClass();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        // 执行，并获取操作相关信息
        LocalDateTime startTime = LocalDateTime.now();
        boolean status = true;
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            status = false;
            throw e;
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            Log log = new Log();
            ApiOperation apiOperationAnnotation = method.getAnnotation(ApiOperation.class);
            if (apiOperationAnnotation != null) {
                log.setSummary(apiOperationAnnotation.value());
            }
            log.setUri(request.getRequestURI());
            log.setMethod(request.getMethod());
            log.setUsername(request.getRemoteUser());
            log.setIp(request.getRemoteAddr());
            log.setParameter(getParameter(method, joinPoint.getArgs()));
            log.setTime(startTime);
            log.setSpendTime(Duration.between(startTime, endTime).toMillis());
            log.setStatus(status);

            // 通过logback保存
            Logger logger = LoggerFactory.getLogger(aClass);
            logger.info(log.toString());

            // 通过MongoDB保存
            DisableLogDBStorage annotationInMethod = method.getAnnotation(DisableLogDBStorage.class);
            DisableLogDBStorage annotationInClass = aClass.getAnnotation(DisableLogDBStorage.class);
            if ((annotationInMethod == null && annotationInClass == null)
                    || (!log.getStatus() && annotationInMethod != null && annotationInMethod.enableWhenError())
                    || (!log.getStatus() && annotationInClass != null && annotationInClass.enableWhenError())) {
                logService.add(log);
            }
        }
        return result;
    }

    /**
     * <p>
     * 根据方法和传入的参数获取请求参数
     * </p>
     * <p>
     * args = joinPoint.getArgs()表示传入的实参
     * parameters = method.getParameters()表示方法的形参
     * </p>
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (args[i] == null) {
                continue;
            }
            // 若形参用@RequestBody修饰
            RequestBody requestBodyAnnotation = parameters[i].getAnnotation(RequestBody.class);
            if (requestBodyAnnotation != null) {
                argList.add(args[i]);
            }
            // 若形参用@RequestParam修饰
            RequestParam requestParamAnnotation = parameters[i].getAnnotation(RequestParam.class);
            if (requestParamAnnotation != null) {
                Map<String, Object> map = new HashMap<>();
                String key = StringUtils.hasLength(requestParamAnnotation.value()) ? requestParamAnnotation.value() : parameters[i].getName();
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        return argList
                .stream()
                .map(o -> {
                    try {
                        return objectMapper.writeValueAsString(o);
                    } catch (JsonProcessingException e) {
                        return null;
                    }
                })
                .collect(Collectors.joining(", "));
    }

}
