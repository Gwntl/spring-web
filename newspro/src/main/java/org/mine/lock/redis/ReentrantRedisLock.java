package org.mine.lock.redis;

import org.mine.rule.redis.RedisRuler;
import org.springframework.stereotype.Component;

/**
 * 可重入锁
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: reentrantRedisLock
 * @date 2020/11/278:53
 */
@Component
public class ReentrantRedisLock extends RedisLock {

    @Override
    public boolean lock(RedisLockInput input) {
        Long times = 0L;
        if (input.getRetryTimes() > 0L) {
            times = input.getRetryTimes();
        }
        String redisKey = RedisRuler.doCreateKey(input.getLogicDesc(), input.getKey());
        String countKey = RedisRuler.doCreateCountKey(input.getKey());
        while(!getRedisCache().lock(redisKey, countKey, input.getValue(), input.getExpire().toString())) {
            if (times < 0L) {
                return false;
            } else {
                waitMoment();
            }
            if (times > 0L) {
                times--;
            }
        }
        return true;
    }

    @Override
    public boolean tryLock(RedisLockInput input) {
        String redisKey = RedisRuler.doCreateKey(input.getLogicDesc(), input.getKey());
        String countKey = RedisRuler.doCreateCountKey(input.getKey());
        return getRedisCache().lock(redisKey, countKey, input.getValue(), input.getExpire().toString());
    }

    @Override
    public boolean hasLock(RedisLockInput input) {
        return getRedisCache().hasLock(RedisRuler.doCreateKey(input.getLogicDesc(), input.getKey()), input.getValue());
    }

    @Override
    public void unlock(RedisLockInput input) {
        String redisKey = RedisRuler.doCreateKey(input.getLogicDesc(), input.getKey());
        String countKey = RedisRuler.doCreateCountKey(input.getKey());
        getLog().debug("redisKey[{}] -> value : {}.", redisKey, getRedisCache().getVal(redisKey));
        getLog().debug("countKey[{}] -> value : {}", countKey, getRedisCache().getVal(countKey));
        getRedisCache().unLock(redisKey, countKey, input.getValue());
    }
}
