package org.mine.lock.redis;

import org.mine.aplt.util.CommonUtils;
import org.mine.rule.redis.RedisRuler;
import org.springframework.stereotype.Component;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: DefaultRedisLock
 * @date 2020/11/2015:04
 */
@Component
public class DefaultRedisLock extends RedisLock {

    @Override
    public boolean lock(RedisLockInput input) {
        Long times = 0L;
        if (input.getRetryTimes() > 0L) {
            times = input.getRetryTimes();
        }
        String redisKey = RedisRuler.doCreateKey(input.getLogicDesc(), input.getKey());
        while(!getRedisCache().lock(redisKey, input.getValue(), input.getExpire() + "")) {
            if (times < 0L) {
                return false;
            } else {
                waitMoment();
            }
            if (times > 0L) {
                times--;
            }
        }
        getLog().debug("Default redisKey : {}, value : {}.", redisKey, input.getValue());
        if (CommonUtils.isNotEmpty(redisKey)) redisLockCache.put(redisKey, input.getValue());
        return true;
    }

    @Override
    public boolean tryLock(RedisLockInput input) {
        String redisKey = "";
        try {
            redisKey = RedisRuler.doCreateKey(input.getLogicDesc(), input.getKey());
            getLog().debug("Default redisKey : {}, value : {}.", redisKey, input.getValue());
            return getRedisCache().lock(redisKey, input.getValue(), input.getExpire() + "");
        } finally {
            if (CommonUtils.isNotEmpty(redisKey)) redisLockCache.put(redisKey, input.getValue());
        }
    }

    @Override
    public boolean hasLock(RedisLockInput input) {
        return getRedisCache().existKey(RedisRuler.doCreateKey(input.getLogicDesc(), input.getKey()));
    }

    @Override
    public void unlock(RedisLockInput input) {
        String redisKey = "";
        try {
            redisKey = RedisRuler.doCreateKey(input.getLogicDesc(), input.getKey());
            getLog().debug("Default redisKey[{}] -> value : {}.", redisKey, getRedisCache().getVal(redisKey));
            getRedisCache().unLock(redisKey, redisLockCache.get(redisKey));
        } finally {
            if (CommonUtils.isNotEmpty(redisKey)) redisLockCache.remove(redisKey);
        }
    }

    //缓存穿透及缓存击穿的代码书写.
//    public String getData(String condition) {
//        String result = "";
//        String key = redisLock.getKey(condition);
//        int expire = ThreadLocalRandom.current().nextInt(120, 300);
//        //未取到缓存时重试次数
//        int retry = 9;
//        while (retry > 0) {
//            result = redisCache.getCacheData(key);
//            if (CommonUtils.isEmpty(result)) {
//                //以key为锁颗粒度, 不影响其他线程获取不同的key时导致阻塞.
//                if (redisLock.tryLock(key)) {
//                    try {
//                        //当getDBData()值为null时也一样放入redis中. 防止缓存穿透时DB数据压力过大. 使用此方案考虑到并发量不大及信息输入错误.
//                        String dbResult = "";
//                        try {
//                            dbResult = getDBData();
//                        } catch (MineException e) {
//                            if (e.getError_code().equals("NOT_FOUND")) {
//                                //当前仅捕获NOT_FOUND异常
//                                dbResult = null;
//                            } else {
//                                throw e;
//                            }
//                        }
//                        redisCache.put(key, dbResult, expire);
//                        result = redisCache.getCacheData(key);
//                        break;
//                    } finally {
//                        redisLock.unLock(key);
//                    }
//                } else {
//                    result = rediscache.getCacheData(key);
//                    if (CommonUtils.isEmpty(result)) {
//                        try {
//                            TimeUnit.MILLISECONDS.sleep(100);
//                            retry--;
//                        } catch (InterruptedException e) {
//                        }
//                    } else {
//                        break;
//                    }
//                }
//            } else {
//                break;
//            }
//        }
//        return result;
//    }
}
