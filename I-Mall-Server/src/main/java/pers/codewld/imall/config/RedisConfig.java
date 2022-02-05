package pers.codewld.imall.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * <p>
 * Redis 配置类
 * </p>
 *
 * @author codewld
 * @since 2022-02-05
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    RedisSerializer<Object> jsonSerializer;

    /**
     * 自定义Redis操作类
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置键值对的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(jsonSerializer);
        // 设置hash中键值对的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(jsonSerializer);
        // 调用afterPropertiesSet()方法，确保连接已建立
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 缓存管理器
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string())) // 设置键序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer)) // 设置值序列化方式
                .entryTtl(Duration.ofHours(1)) // 设置缓存有效期
                .disableCachingNullValues(); // 不缓存空值
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    /**
     * JSON 序列化器
     */
    @Bean
    public RedisSerializer<Object> RedisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // JavaTimeModule用于处理LocalDate类型
        objectMapper.registerModule(new JavaTimeModule());
        // ALL表示序列化对象中的所有成员（包括getter、setter、field等）；ANY表示序列化所有可见度的成员（包括private）
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // LaissezFaireSubTypeValidator.instance表示：不做验证，允许序列化所有实例
        // ObjectMapper.DefaultTyping.NON_FINAL表示：除了final声明的值和基本类型外，都会在序列化时添加类名标识（final不应被序列化）
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    /**
     * 配置默认键生成类
     */
    @Override
    public KeyGenerator keyGenerator() {
        return myKeyGenerator();
    }

    /**
     * 自定义键生成类 [类名_方法名_参数]
     */
    KeyGenerator myKeyGenerator(){
        return (target, method, params) -> target.getClass().getSimpleName() + "_"
                + method.getName() + "_"
                + StringUtils.arrayToDelimitedString(params, "_");
    }

}
