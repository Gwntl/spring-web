package com.threadPool;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: MThreadCacheTest
 * @date 2020/12/29:55
 */
public class MThreadCacheTest {
    private static final Map<String, String> map = new ConcurrentHashMap<>(1024);

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        char[] cs = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        Random r = new Random();
        int length = cs.length;
        for (int i = 0; i < 15; i++) {
            es.submit(new MThreadWorker(cs[r.nextInt(length)] + ":" + i, i));
            try {
                TimeUnit.MILLISECONDS.sleep(400);
            } catch (InterruptedException e) {
            }
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
        es.shutdown();
    }

    static class MThreadWorker implements Runnable {
        private String name;
        private int i;

        public MThreadWorker(String name, int i) {
            this.name = name;
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println("插入: " + i);
            map.put(this.name, this.name + "_" + i);
        }
    }


    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (map) {
                        if (map.size() > 0) {
                            Iterator<String> iterator = map.keySet().iterator();
                            System.out.println("begin>>>>>>>>");
                            while (iterator.hasNext()) {
                                String next = "";
                                System.out.println((next = iterator.next()) + "=" + map.get(next));
                                try {
                                    TimeUnit.SECONDS.sleep(1);
                                } catch (InterruptedException e) {
                                }
                                iterator.remove();
                            }
                            System.out.println("end>>>>>>>>");
                        } else {
                            System.out.println("empty>>>>>>>>>>>>>>>>>>>");
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }
            }
        }).start();
    }
}
