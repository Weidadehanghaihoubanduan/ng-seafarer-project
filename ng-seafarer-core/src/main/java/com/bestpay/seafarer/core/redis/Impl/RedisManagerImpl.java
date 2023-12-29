package com.bestpay.seafarer.core.redis.Impl;

import com.bestpay.seafarer.core.redis.RedisManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
@Service
public class RedisManagerImpl implements RedisManager {

    @Resource private StringRedisTemplate redisTemplate;

    /**
     * 是否存在key
     * @param key key
     * @return true or false
     */
    public Boolean redisHasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置指定 key 的值
     * @param key   key
     * @param value value
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void redisKeySet(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取指定 key 的值
     * @param key key
     * @return java.lang.String
     */
    public String redisKeyGet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 返回 key 的剩余的过期时间(key过期会主动删除)
     * @param key key
     * @return Long
     */
    public Long redisKeyGetExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }
}
