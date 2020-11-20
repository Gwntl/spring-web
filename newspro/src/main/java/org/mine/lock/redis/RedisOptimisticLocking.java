package org.mine.lock.redis;

import org.mine.aplt.Cache.RedisCache;
import org.mine.rule.redis.RedisKeyRuler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: RedisOptimisticLocking
 * @date 2020/11/2015:04
 */
@Component
public class RedisOptimisticLocking extends RedisLock {
    @Autowired
    private RedisCache redisCache;

    @Override
    void lock(String key, Object value) {
        if (redisCache.setValIfAbsent(RedisKeyRuler.doCreateKey("", key, RedisKeyRuler.VALUE_TYPE_STRING), value)) {

        }
    }

    @Override
    void tryLock(String key, Object value) {

    }

    @Override
    void hasLock(String key) {

    }

    @Override
    void unlock(String key) {

    }
}
