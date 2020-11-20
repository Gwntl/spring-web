package org.mine.aplt.Cache;

import org.mine.aplt.redis.RedisClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: RedisCache
 * @date 2020/11/1819:58
 */
@Component
public class RedisCache {
    @Autowired
    private RedisClientUtil clientUtil;

    /**
    * 判断键是否存在
    * @param key
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public boolean existKey(String key) {
        return clientUtil.existsKey(key);
    }

    /**
    * 删除键
    * @param key
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public boolean delKey(String key) {
        return clientUtil.delKey(key);
    }

    /**
    * 删除键
    * @param keys
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public boolean delKey(List<String> keys) {
        return clientUtil.delKeys(keys);
    }

    /**
    * 字符串类型缓存
    * @param key
    * @param value
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public boolean setVal(String key, Object value) {
        return clientUtil.set(key, value);
    }

    /**
    * 字符串缓存
    * @param caches
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public boolean setVal(Map<String, Object> caches) {
        return clientUtil.multiSet(caches);
    }

    /**
    * 当键不存在时才设置值
    * @param key
    * @param value
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public boolean setValIfAbsent(String key, Object value) {
        return clientUtil.setIfAbsent(key, value);
    }

    /**
    * 键不存在时设置值.
    * @param caches
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public boolean setValIfAbsent(Map<String, Object> caches) {
        return clientUtil.multiSetIfAbsent(caches);
    }

    /**
    * 字符串类型缓存并且设置超时时间
    * @param key
    * @param value
    * @param timeOut
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public boolean setValWithExpire(String key, Object value, Long timeOut) {
        return clientUtil.set(key, value, timeOut, TimeUnit.SECONDS);
    }

    /**
    * 获取缓存值
    * @param key
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public Object getVal(String key) {
        return clientUtil.get(key);
    }

    /**
    * 获取缓存值
    * @param keys
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public List<Object> getVal(List<String> keys) {
        return clientUtil.multiGet(keys);
    }

    /**
    * 设置新值返回旧值
    * @param key
    * @param value
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public Object getAndSet(String key, Object value) {
        return clientUtil.getAndSet(key, value);
    }

    /**
    * 设置过期时间
    * @param key
    * @param timeOut
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public boolean expire(String key, Long timeOut) {
        return clientUtil.expire(key, timeOut);
    }

    /**
    * 删除过期时间，使键持久化.
    * @param key
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public boolean persist(String key) {
        return clientUtil.persist(key);
    }

    /**
    * 获取键剩余过期时间
    * @param key
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public Long getExpire(String key) {
        return clientUtil.ttl(key);
    }

    public Boolean setLockInfo(String key, Object value, Long expireTime) {
        org.springframework.data.redis.cache.RedisCache redisCache =
                new org.springframework.data.redis.cache.RedisCache(key, null, new RedisTemplate<>(), expireTime);
        redisCache.put(key, value);
        return true;
    }

//    private static volatile RedisClientUtil util = null;
//    private RedisCache(){}
    /**
    * 单例
    * @return: org.mine.aplt.redis.RedisClientUtil
    * @Author: wntl
    * @Date: 2020/11/19
    */
//    public static RedisClientUtil getRedisClientUtil() {
//        if (util == null) {
//            synchronized (RedisCache.class) {
//                if (util == null) {
//                    util = new RedisClientUtil();
//                }
//            }
//        }
//        return util;
//    }
}