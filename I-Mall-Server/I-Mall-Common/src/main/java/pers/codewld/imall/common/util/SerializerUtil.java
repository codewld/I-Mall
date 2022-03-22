package pers.codewld.imall.common.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 序列化 工具类
 * </p>
 *
 * @author codewld
 * @since 2022-03-22
 */
@Component
public class SerializerUtil {

    /**
     * 序列化器
     */
    static ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            // JavaTimeModule用于处理LocalDate类型
            objectMapper.registerModule(new JavaTimeModule());
            // ALL表示序列化对象中的所有成员（包括getter、setter、field等）；ANY表示序列化所有可见度的成员（包括private）
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            // LaissezFaireSubTypeValidator.instance表示：不做验证，允许序列化所有实例
            // ObjectMapper.DefaultTyping.NON_FINAL表示：除了final声明的值和基本类型外，都会在序列化时添加类名标识（final不应被序列化）
            objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        }
        return objectMapper;
    }

    /**
     * 序列化
     */
    public String serialize(Object obj) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(obj);
    }

    /**
     * 反序列化
     */
    public Object deSerialize(String str) throws JsonProcessingException {
        return getObjectMapper().readValue(str, Object.class);
    }

}
