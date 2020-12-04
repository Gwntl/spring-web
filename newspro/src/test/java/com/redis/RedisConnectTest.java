package com.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mine.aplt.Cache.RedisCache;
import org.mine.aplt.redis.RedisClientUtil;
import org.mine.aplt.support.bean.GitContext;
import org.mine.lock.SystemIdWin;
import org.mine.lock.redis.DefaultRedisLock;
import org.mine.lock.redis.RedisLockInput;
import org.mine.lock.redis.RedisLogicDecrConstant;
import org.mine.rule.redis.RedisRuler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: RedisConnectTest
 * @date 2020/11/610:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/base/application-context-impl.xml"})
public class RedisConnectTest {
    @Autowired
    RedisClientUtil clientUtil;
    @Autowired
    RedisCache cache;
    @Autowired
    DefaultRedisLock locking;
    @Autowired
    SystemIdWin systemIdWin;
    @Test
    public void test() throws InterruptedException {
//        System.out.println("------------------------");
//        System.out.println(">>>>>>>>>>>>>>>>" + client.get("test"));
//        cache.setVal("test", "test3");
//        System.out.println(">>>>>>>>>>>>>>>>" + client.get("test"));
//        System.out.println(">>>>>>>>>>>>>>>>" + client.get("test_incr"));
//        client.set("test_incr", "1");
//        System.out.println(">>>>>>>>>>>>>>>>" + client.get("test_incr"));
//        System.out.println(">>>>>>>>>>>>>>>>" + client.increment("test_incr"));
//        if(cache.lock("test", "a", "100")){
//            System.out.println("setNx success.");
//        } else {
//            System.out.println("setNx error.");
//        }
//        System.out.println(">>>>>>>>>>>>>>>>" + cache.getVal("test"));
//
//        System.out.println(cache.unLock("test", "a"));
//        System.out.println(">>>>>>>>>>>unlock>>>>>" + cache.getVal("test"));
//        if(locking.tryLock("autowired.test", "lock", "a", "100")) {
//            System.out.println("setNx success.");
//        } else {
//            System.out.println("setNx error.");
//        }
//        System.out.println(locking.getSystemIdWin().systemID());
//        String key = RedisRuler.doCreateKey("only.key", "test", RedisRuler.VALUE_TYPE_STRING);
//        String hashKey = systemIdWin.systemID();
//        String countKey = RedisRuler.doCreateCountKey(key);
//        String value = RedisRuler.doCreateValue("only", hashKey);
//        String expire = "500";
//        cache.lock(key, value, Long.valueOf(expire));
//        cache.unLock(key, value);
//        cache.unLock(key, hashKey, countKey, value);
//        cache.lock(key, hashKey, countKey, value, expire);
//        System.out.println("String1 : key : " + cache.getVal(key));
//        System.out.println("String1 : expire : " + clientUtil.ttl(key));
//        System.out.println("String1 : key : " + cache.getVal(countKey));
//        System.out.println("Hash1 : Info : " + clientUtil.hashGetAll(hashKey));
//        TimeUnit.SECONDS.sleep(2);
//        cache.unLock(key, hashKey, countKey, value);
//        cache.lock(key, hashKey, countKey, value, expire);
//        System.out.println("String2 : key : " + cache.getVal(key));
//        System.out.println("String1 : expire : " + clientUtil.ttl(key));
//        System.out.println("String2 : key : " + cache.getVal(countKey));
//        System.out.println("Hash2 : Info : " + clientUtil.hashGetAll(hashKey));
//        TimeUnit.SECONDS.sleep(2);
//        cache.unLock(key, hashKey, countKey, value);
//        cache.lock(key, hashKey, countKey, value, expire);
//        System.out.println("String3 : key : " + cache.getVal(key));
//        System.out.println("String1 : expire : " + clientUtil.ttl(key));
//        System.out.println("String3 : key : " + cache.getVal(countKey));
//        System.out.println("Hash3 : Info : " + clientUtil.hashGetAll(hashKey));

        DefaultRedisLock lock = GitContext.getBean(DefaultRedisLock.class);
        int threadNums = 5;
        ExecutorService service = Executors.newFixedThreadPool(threadNums);
        for (int i = 0; i < threadNums; i++) {
            service.submit(new Worker(lock, "JOB_" + i));
        }
        TimeUnit.SECONDS.sleep(1);
        service.shutdown();

    }

    public static class Worker implements Runnable {
        DefaultRedisLock lock;
        String jobID;
        public Worker(DefaultRedisLock lock, String jobID) {
            this.lock = lock;
            this.jobID = jobID;
        }

        @Override
        public void run() {
            RedisLockInput input = new RedisLockInput();
            input.setLogicDesc(RedisLogicDecrConstant.QUARTZ_TIMING_TASK_LOGIC).setKey(jobID)
                    .setValue(RedisRuler.doCreateValue(RedisLogicDecrConstant.QUARTZ_TIMING_TASK_LOGIC, jobID))
                    .setExpire(180L).setRetryTimes(0L);
            System.out.println("Thread.currentThread().getId() : " + Thread.currentThread().getId());
            lock.lock(input);
        }
    }
}
