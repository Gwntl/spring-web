package org.mine.aplt.Cache;

import org.mine.aplt.exception.RedisException;
import org.mine.aplt.redis.RedisClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存.
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

    /**
    * 获取哈希表中对应的值
    * @param key
    * @param field
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public Object hashGet(String key, String field) {
        return clientUtil.hashGet(key, field);
    }

    public Map<Object, Object> hashGetAll(String key) {
        return clientUtil.hashGetAll(key);
    }

    /**
    * 获取redis锁, 使用redis自带命令实现。弃用, 不建议使用
    * @param key
    * @param value
    * @param timeOut
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/26
    */
    @Deprecated
    public Boolean lock(String key, Object value, Long timeOut) {
        return clientUtil.setIfAbsent(key, value, timeOut);
    }

    /**
    * 获取redis锁，使用LUA脚本实现
    * @param key
    * @param value
    * @param expire
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public Boolean lock(String key, Object value, String expire) {
        List<String> list = new ArrayList<>();
        list.add(key);
        String script = "if redis.call('set',KEYS[1], ARGV[1], 'ex', ARGV[2], 'nx') then return 1 else return 0 end";
        return clientUtil.eval(script, list, Long.class, value, expire) != 0L;
    }

    /**
    * 获取redis可重入锁，使用LUA脚本实现.
    * @param key
    * @param countKey
    * @param val
    * @param expire
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public Boolean lock(String key, String countKey, Object val, String expire) {
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(countKey);
        String script = "if redis.call('set', KEYS[1], ARGV[1], 'ex', ARGV[2], 'nx') then redis.call('set', KEYS[2], ARGV[3], 'ex', ARGV[2]) " +
                "else if redis.call('get', KEYS[1]) == ARGV[1] then redis.call('incr', KEYS[2]) else return 0 end end return 1";
        return clientUtil.eval(script, list, Long.class, val, expire, "0") != 0L;
    }

    public boolean hasLock(String key, Object val) {
        List<String> list = new ArrayList<>();
        list.add(key);
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return 1 else return 0 end";
        return clientUtil.eval(script, list, Long.class, val) != 0L;
    }


    /**
    * 释放redis锁，为保证命令的原子性使用LUA脚本实现.
    * @param key
    * @param value
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public void unLock(String key, Object value) {
        List<String> list = new ArrayList<>();
        list.add(key);
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then redis.call('del', KEYS[1]) else return 0 end return 1";
        if (clientUtil.eval(script, list, Long.class, value) == 0L) {
            throw RedisException.REDIS_EVAL_ERROR(String.format("指定key[%s]或value[%s]在redis中不存在或者其他异常, 请检查!!", key, value));
        }
    }

    /**
    * 释放redis可重入锁
    * @param key
    * @param countKey
    * @param value
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public void unLock(String key, String countKey, Object value) {
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(countKey);
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then if redis.call('get', KEYS[2]) == ARGV[2] then " +
                "redis.call('del', KEYS[1]) redis.call('del', KEYS[2]) else redis.call('decr', KEYS[2]) end else return 0 end return 1";
        if (clientUtil.eval(script, list, Long.class, value, "0") == 0L) {
            throw RedisException.REDIS_EVAL_ERROR(String.format("指定key[%s/%s]或value[%s]在redis中不存在或者其他异常, 请检查!!",
                    key, countKey, value));
        }
    }
}