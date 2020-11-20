package com.threadPool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: ConcurrentHashMapTest
 * @date 2020/8/2515:15
 */
public class ConcurrentHashMapTest {
    public static ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);

        new Thread(() -> {
            while (true) {
                if (map.size() > 0 && map.containsKey("key")) {
                    System.out.println(map.get("key"));
                }
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            service.submit(new Worker("key", i));
        }
    }

    static class Worker implements Runnable {
        private String key;
        private Object value;
        public Worker(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            map.put(key, value);
        }
    }
}
