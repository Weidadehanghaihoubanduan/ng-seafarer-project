package com.bestpay.seafarer.core.redis;

import java.util.concurrent.TimeUnit;

/**
 * @author dengyancan
 * @Date: 2023-12-29
 */
public interface RedisManager {

    /**
     * 是否存在key
     * @param key key
     * @return true or false
     */
    Boolean redisHasKey(String key);

    /**
     * 设置指定 key 的值
     * @param key   key
     * @param value value
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    void redisKeySet(String key, String value, long timeout, TimeUnit unit);


    /**
     * 获取指定 key 的值
     * @param key key
     * @return java.lang.String
     */
    String redisKeyGet(String key);

    /**
     * 返回 key 的剩余的过期时间(key过期会主动删除)
     * @param key key
     * @return Long
     */
    Long redisKeyGetExpire(String key, TimeUnit timeUnit);
}
