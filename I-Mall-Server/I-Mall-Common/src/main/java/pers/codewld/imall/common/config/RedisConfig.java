package pers.codewld.imall.common.config;

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
import pers.codewld.imall.common.util.SerializerUtil;

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
    SerializerUtil serializerUtil;

    /**
     * 自定义Redis操作类
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置键值对的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(getJsonSerializer());
        // 设置hash中键值对的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(getJsonSerializer());
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
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getJsonSerializer())) // 设置值序列化方式
                .entryTtl(Duration.ofHours(1)) // 设置缓存有效期
                .disableCachingNullValues(); // 不缓存空值
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    /**
     * JSON 序列化器
     */
    RedisSerializer<Object> jsonSerializer;

    public RedisSerializer<Object> getJsonSerializer() {
        if (jsonSerializer == null) {
            Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
            serializer.setObjectMapper(serializerUtil.getObjectMapper());
            jsonSerializer = serializer;
        }
        return jsonSerializer;
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
