package com.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mine.aplt.Cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    RedisCache cache;
    @Test
    public void test() {
        System.out.println("------------------------");
//        System.out.println(">>>>>>>>>>>>>>>>" + client.get("test"));
        cache.setVal("test", "test3");
//        System.out.println(">>>>>>>>>>>>>>>>" + client.get("test"));
//        System.out.println(">>>>>>>>>>>>>>>>" + client.get("test_incr"));
//        client.set("test_incr", "1");
//        System.out.println(">>>>>>>>>>>>>>>>" + client.get("test_incr"));
//        System.out.println(">>>>>>>>>>>>>>>>" + client.increment("test_incr"));
    }
}
