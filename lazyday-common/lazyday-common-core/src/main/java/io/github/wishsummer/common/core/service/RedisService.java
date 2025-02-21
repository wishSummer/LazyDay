package io.github.wishsummer.common.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author < a href="wangfc@hzwesoft.com">wangfc</ a>
 * @version $ Id: RedisService.java, 2023/12/28 17:32 $
 */
@Component
public class RedisService {

    public RedisTemplate redisTemplate;

    /**
     * 缓存基本对象
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本对象
     *
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     * @param <T>
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 缓存list数据
     *
     * @param <T>
     * @return 缓存对象总数
     */
    public <T> Long setCacheList(final String key, final List<T> value) {
        Long count = redisTemplate.opsForList().rightPushAll(key, value);
        return count == null ? 0 : count;
    }

    /**
     * 缓存Set
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> value) {
        BoundSetOperations<String, T> boundSetOperations = redisTemplate.boundSetOps(key);
        for (T t : value) {
            boundSetOperations.add(t);
        }
        return boundSetOperations;
    }

    /**
     * 缓存Map
     */
    public <T> void setCacheMap(final String key, final Map<String, T> value) {
        if (value != null) {
            redisTemplate.opsForHash().putAll(key, value);
        }
    }

    /**
     * 向Hash中存入数据
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取基本对象
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 获取缓存的set
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取缓存的map
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取Hash中的数据
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKey) {
        return redisTemplate.opsForHash().multiGet(key, hKey);
    }


    /**
     * 获取缓存的list对象
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 获取缓存的基本对象列表
     */
    public Collection<String> getPrefixKeys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除单个对象
     */
    public Boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     */
    public Boolean deleteObject(final Collection collectionKey) {
        return redisTemplate.delete(collectionKey) > 0;
    }

    /**
     * 删除Hash中的某条数据
     */
    public Boolean deleteVacheMapValue(final String key, final String hKey) {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    /**
     * 设置有效时间，单位分钟
     *
     * @param key     键
     * @param timeout 值
     */
    public Boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.MINUTES);
    }

    /**
     * 设置有效时间
     */
    public Boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取指定key的有效时间
     */
    public long getExpire(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断key是否存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
