package pers.codewld.imall.controller.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pers.codewld.imall.model.vo.ResultVO;

import java.util.Map;

/**
 * <p>
 * 全局统一响应处理类
 * </p>
 * <p>
 * 将接口的返回结果加工为统一响应体
 * </p>
 *
 * @author codewld
 * @since 2022-02-13
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 放行404响应
        if (body instanceof Map && ((Map<?, ?>) body).get("status").equals(404)) {
            return body;
        }
        // 若原返回结果为String，需要生成响应体、转换为JSON，再返回
        if (body instanceof String || String.class.equals(returnType.getGenericParameterType())) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            try {
                return mapper.writeValueAsString(ResultVO.success(body));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // 如果已经包装为响应体，直接返回
        if (body instanceof ResultVO) {
            return body;
        }
        return ResultVO.success(body);
    }
}
