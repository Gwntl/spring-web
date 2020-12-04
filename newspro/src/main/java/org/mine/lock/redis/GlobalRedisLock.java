package org.mine.lock.redis;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: GlobalRedisLock
 * @date 2020/11/2710:31
 */
public class GlobalRedisLock extends RedisLock {
    @Override
    public boolean lock(RedisLockInput input) {
        return false;
    }

    @Override
    public boolean tryLock(RedisLockInput input) {
        return false;
    }

    @Override
    public boolean hasLock(RedisLockInput input) {
        return false;
    }

    @Override
    public void unlock(RedisLockInput input) {

    }
}
