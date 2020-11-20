package com.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.StandardCharsets;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: SRedisCacheTest
 * @date 2020/11/2015:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/base/application-context-impl.xml"})
public class SRedisCacheTest {
    @Autowired
    private RedisTemplate template;
    @Test
    public void Otest() {
        RedisCache cache = new RedisCache("otest", "prefix".getBytes(StandardCharsets.UTF_8), template, 10L);
        System.out.println("cacheName : " + cache.getName());
        RedisOperations operations = (RedisOperations) cache.getNativeCache();
        operations.opsForValue().set("cache:09:string", "temp");
        System.out.println("=====================" + operations.getClass().getName());
        cache.put("cache:10:string", "temp");

    }
}
