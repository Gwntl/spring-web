package com.threadPool;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: ProducerTest
 * @date 2020/8/259:54
 */
public class ProducerTest {
    /**
     * LinkedBlockingQueue在使用生产者消费者模式并且使用offer和put结合使用时,
     * 在lock锁下, 当queue队列满时, 使用put时会导致锁不释放，使得其他线程无法获取锁.
     */
    private static BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    static {
//        new Thread(()->{
//            while (true) {
//                try {
//                    lock.properties.lock.properties();
////                    System.out.println("获取锁................");
//                    if (queue.size() > 0) {
//                        System.out.println("获取值: " + queue.poll() + ", size = " + queue.size());
//                        conditionB.signalAll();
//                    } else {
////                        System.out.println("等待中.............");
//                        condition.await();
////                        System.out.println("被唤醒............");
//                    }
//                } catch(InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    lock.properties.unlock();
//                }
//            }
//        }, "PRODUCER_THREAD").start();

        new Thread(()->{
            while (true) {
                try {
                    String value = queue.take();
                    System.out.println("被唤醒......");
                    System.out.println("获取值: " + value + ", size = " + queue.size());
                } catch(InterruptedException e) {
                }
            }
        }, "PRODUCER_THREAD").start();
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1; i++) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//
//            }
            service.submit(new Worker("" + i, i % 7));
        }
        System.out.println("main执行完毕.");
    }

    static class Worker implements Runnable{
        private String message;
        private long waitTime;
        public Worker(String message, long waitTime) {
            this.message = message;
            this.waitTime = waitTime;
        }

        @Override
        public void run() {
//            try {
//                lock.properties.lock.properties();
//                System.out.println("waitTime : " + waitTime);
////                TimeUnit.SECONDS.sleep(waitTime);
//                while(!queue.offer(message)) {
//                    System.out.println(Thread.currentThread().getName() + ", put value 等待中.......");
//                    conditionB.await();
//                    System.out.println(Thread.currentThread().getName() + ", put value 被唤醒.......");
////                    System.out.println("put value.");
//                    //此处会导致锁不释放. 使得循环线程中无法获取锁从而导致queue队列中的数据无法插入获取. 程序会夯在此处.
////                    queue.put(message);
//                }
////                System.out.println("唤醒........");
//                condition.signal();
//            } catch(Exception e) {
//                e.printStackTrace();
//            } finally {
//                lock.properties.unlock();
//            }

            try {
//                System.out.println("执行中...........");
//                System.out.println("waitTime : " + waitTime);
                TimeUnit.SECONDS.sleep(5);
//                System.out.println("插入值 : " + message);
                if(!queue.offer(message)) {
                    queue.put(message);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
