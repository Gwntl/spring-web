package org.mine.lock.redis;

import org.mine.lock.Lock;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: RedisLock
 * @date 2020/11/2014:57
 */
public abstract class RedisLock implements Lock {

    abstract void lock(String key, Object value);

    abstract void tryLock(String key, Object value);

    abstract void hasLock(String key);

    abstract void unlock(String key);

    /**
     * 用于装载数据
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        loadCacheData();
    }
    public void loadCacheData() {}
}
