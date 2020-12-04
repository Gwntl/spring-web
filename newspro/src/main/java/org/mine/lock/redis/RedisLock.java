package org.mine.lock.redis;

import org.mine.aplt.Cache.RedisCache;
import org.mine.lock.Lock;
import org.mine.lock.SystemIdWin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: RedisLock
 * @date 2020/11/2014:57
 */
public abstract class RedisLock implements Lock {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static final Map<String, Object> redisLockCache = new ConcurrentHashMap<>(1024);
    private SystemIdWin systemIdWin;
    private RedisCache redisCache;
    /**
     * @return the systemIdWin as systemIdWin
     */
    public SystemIdWin getSystemIdWin() {
        return systemIdWin;
    }

    /**
     * @param systemIdWin the systemIdWin to set
     */
    @Autowired
    public void setSystemIdWin(SystemIdWin systemIdWin) {
        this.systemIdWin = systemIdWin;
    }

    /**
     * @return the redisCache as redisCache
     */
    public RedisCache getRedisCache() {
        return redisCache;
    }

    /**
     * @param redisCache the redisCache to set
     */
    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
    * 获取锁. 当重试次数等于0时表示阻塞获取锁直到成功为止.
    * @param input
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/23
    */
    public abstract boolean lock(RedisLockInput input);

    /**
    * 尝试获取锁，立即返回.
    * @param input
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/23
    */
    public abstract boolean tryLock(RedisLockInput input);

    /**
    * 判断当前线程是否持有锁
    * @param input
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/23
    */
    public abstract boolean hasLock(RedisLockInput input);

    /**
    * 解锁
    * @param input
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/23
    */
    public abstract void unlock(RedisLockInput input);

    /**
     * @return the log as log
     */
    public Logger getLog() {
        return log;
    }

    /**
     * 用于装载数据
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        loadCacheData();
    }
    public void loadCacheData() {

    }
}
