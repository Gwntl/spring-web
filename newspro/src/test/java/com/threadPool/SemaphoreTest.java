package com.threadPool;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: SemaphoreTest
 * @date 2020/9/220:26
 */
public class SemaphoreTest {
    private static ThreadPoolExecutor poolExecutor_1 = new ThreadPoolExecutor(10, 50, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(30),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "SEM_WORKER_O");
                }
            });
    private static ThreadPoolExecutor poolExecutor_2 = new ThreadPoolExecutor(10, 50, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(30),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "SEM_WORKER_T");
                }
            });

    private static Map<String, Semaphore> semaphoreMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        int semNum = 30;
        Semaphore semaphore = new Semaphore(4);
        semaphoreMap.put("SEM_TEST", semaphore);
        for (int i = 1; i <= semNum; i++) {
            poolExecutor_1.submit(new Thread(new SemWorker_1(semaphore, i % 6)));
        }
    }

    static class SemWorker_1 implements Runnable {
        Semaphore semaphore;
        int sleepTime;
        public SemWorker_1(Semaphore semaphore, int sleepTime) {
            this.semaphore = semaphore;
            this.sleepTime = sleepTime;
        }
        @Override
        public void run() {
            try {
                semaphore.acquire();
                TimeUnit.SECONDS.sleep(sleepTime);
                System.out.println(System.nanoTime() + " >>>> " + sleepTime + ", " + Thread.currentThread().getName() + "执行完毕, 通知信号量释放连接.");
                poolExecutor_2.submit(new Thread(new SemWorker_2()));
            } catch (InterruptedException e) {

            }
        }
    }

    static class SemWorker_2 implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(">>>信号量释放连接");
                semaphoreMap.get("SEM_TEST").release();
            } catch (InterruptedException e) {

            }
        }
    }

}
