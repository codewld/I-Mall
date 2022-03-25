package pers.codewld.imall.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * <p>
 * Redis 工具类
 * </p>
 *
 * @author codewld
 * @since 2022-02-05
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    SerializerUtil serializerUtil;

    /**
     * 保存属性
     */
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 保存属性
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取属性
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除属性
     */
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除属性
     */
    public Long del(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long time) {
        if (time == 0) {
            return true;
        }
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断是否有该属性
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 递增
     */
    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     */
    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 获取Hash中的属性
     */
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 向Hash中放入一个属性
     */
    public Boolean hSet(String key, String hashKey, Object value, long time) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, time);
    }

    /**
     * 向Hash中放入一个属性
     */
    public void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 直接获取整个Hash
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 直接设置整个Hash
     */
    public Boolean hSetAll(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }

    /**
     * 直接设置整个Hash
     */
    public void hSetAll(String key, Map<String, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 删除Hash中的属性
     */
    public void hDel(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 判断Hash中是否有该属性
     */
    public Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * Hash中属性递增
     */
    public Long hIncr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * Hash中属性递减
     */
    public Long hDecr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    /**
     * 获取Set
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 向Set中添加属性
     */
    public Long sAdd(String key, long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        expire(key, time);
        return count;
    }

    /**
     * 是否为Set中的属性
     */
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取Set的长度
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 删除Set中的属性
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 堵塞式获取队列中的元素，并将该元素备份至备份队列中，待处理成功后删除
     *
     * @param key      队列key
     * @param backKey  备份队列key
     * @param consumer 处理消费者
     */
    public void brPopLPush(String key, String backKey, Consumer<Object> consumer) {
        CompletableFuture.runAsync(() -> {
            RedisConnection connection = RedisConnectionUtils.getConnection(connectionFactory);
            try {
                // 序列化键值
                byte[] keyArr = RedisSerializer.string().serialize(key);
                byte[] backKeyArr = RedisSerializer.string().serialize(backKey);
                Assert.notNull(keyArr, "keyArr不能为null");
                Assert.notNull(backKeyArr, "backKeyArr不能为null");
                while (true) {
                    byte[] res = new byte[0];
                    boolean success = false;
                    try {
                        res = connection.bRPopLPush(0, keyArr, backKeyArr);
                        if (res != null && res.length != 0) {
                            Object o = serializerUtil.deSerialize(new String(res));
                            consumer.accept(o);
                            success = true;
                        }
                    } catch (QueryTimeoutException ignored) {
                        // 防止堵塞式获取行为超时而抛出QueryTimeoutException 异常
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    finally {
                        // 若处理成功，则删除备份队列中的key
                        if (success) {
                            connection.lRem(backKeyArr, 1, res);
                        }
                    }
                }
            } finally {
                RedisConnectionUtils.releaseConnection(connection, connectionFactory);
            }
        });
    }

    /**
     * 获取List中的属性
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取List的长度
     */
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 根据索引获取List中的属性
     */
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 向List中添加属性
     */
    public Long lPush(String key, Object value, long time) {
        Long index = redisTemplate.opsForList().leftPush(key, value);
        expire(key, time);
        return index;
    }

    /**
     * 向List中批量添加属性
     */
    public Long lPushAll(String key, Object... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 向List中批量添加属性
     */
    public Long lPushAll(String key, Long time, Object... values) {
        Long count = redisTemplate.opsForList().leftPushAll(key, values);
        expire(key, time);
        return count;
    }

    /**
     * 从List中移除属性
     */
    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }
}
