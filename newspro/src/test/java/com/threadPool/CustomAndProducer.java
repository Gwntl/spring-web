package com.threadPool;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: CustomAndProducer
 * @date 2020/8/1814:27
 */
public class CustomAndProducer {
    static Lock lock = new ReentrantLock();
    static Condition conditionA = lock.newCondition();
    static CountDownLatch count = new CountDownLatch(1);
    static Object obj = new Object();
    static Map<String, Object> map = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
        /*Thread[] threads = new Thread[10];
        for (Thread thread : threads) {
            thread = new Thread(()->{
                try {
                    blockingQueue.take();

                } catch (InterruptedException e) {
                }
            });
        }*/
        /*AtomicLong count = new AtomicLong(0);
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    blockingQueue.offer("a" + i);
            }
            } catch (Exception e) {

            }
        }).start();

        new Thread(() -> {
            try {
                while(true){
                    String node = blockingQueue.take();
                    count.getAndIncrement();
                    System.out.println(new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date()) + ">>>> " + count.get() + ", "+ node);
                    if (count.get() >= 10L) {
                        break;
                    }
                }
            } catch (InterruptedException e) {

            }
        }).start();*/


        /*for (int i = 0; i < 10; i++) {
            new Thread(new Worker(i)).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    lock.lock();
                    System.out.println("cycle get lock.properties success.............");
                    map.put(i+ "", i);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {

                    }
                    if (i == 8) {
                        map.remove(i + "");
                        throw new RuntimeException("...............");
                    }
                } catch (Exception e) {
                    lock.lock();
                    System.out.println("failed");
                    map.remove("7");
                    lock.unlock();
                } finally {
                    lock.unlock();
                }

                /*synchronized (obj) {
                    System.out.println("cycle get lock.properties success.............");
                    map.put(i+ "", i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {

                    }
                    if (i == 8) {
                        map.remove(i + "");
                    }
                }*/
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                System.out.println("begin into remove cycling......");
//                map.put(i + "_" + i, i + "_0000000");
//                try {
//                    TimeUnit.SECONDS.sleep(3);
//                } catch (InterruptedException e) {
//
//                }
                try {
                    lock.lock();
                    System.out.println("cycle get lock.properties success.............");
                    map.put(i + "_" + i, i + "_0000000");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {

                    }
                } finally {
                    lock.unlock();
                }
//                synchronized (map) {
//                    System.out.println("remove cycle get lock.properties success.............");
//                    map.remove(i+ "");
//                }
            }
        }).start();

    }

    static {
        new Thread(() -> {
            while (true) {
//                test();
                try {
                    lock.lock();
                    test();
                } finally {
                    lock.unlock();
                }
//                synchronized (map) {
//                    test();
//                }
            }
        }).start();
    }

    static void test(){
        if (map.size() > 0) {
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                System.out.println(key + "=" + map.get(key));
                iterator.remove();
            }
        }
    }

    static class Worker implements Runnable{
        private int i;
        public Worker(int i){
            this.i = i;
        }
        @Override
        public void run() {
            try {
                count.countDown();
                TimeUnit.SECONDS.sleep(4);
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
