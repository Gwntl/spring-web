package com.threadPool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: LimitExecuteTest
 * @date 2020/8/1919:36
 */
public class LimitExecuteTest {

    public static void main(String[] args) {
//        new Thread(new Worker(3, 5), "THREAD_LIMIT").start();
        new Thread(new Worker(4, 7), "THREAD_LIMIT_TEST").start();

    }

    public static class Worker implements Runnable{
        int number;
        int executeNumber;
        public Worker(int number, int executeNumber){
            this.number = number;
            this.executeNumber = executeNumber;
        }

        @Override
        public void run() {
            try {
                List<Future<String>> list = new ArrayList<>();
                Semaphore semaphore = new Semaphore(number);
                for (int i = 0; i < executeNumber; i++) {
                    list.add(poolExecutor.submit(new CallWorker(semaphore, Thread.currentThread().getName() + "_" + i, i + 1)));
                }
                poolExecutor.shutdown();
            } catch (Exception e) {

            }
        }
    }

    public static class CallWorker implements Callable<String>{
        Semaphore semaphore;
        String message;
        long time;
        public CallWorker(Semaphore semaphore, String message, long time){
            this.semaphore = semaphore;
            this.message = message;
            this.time = time;
        }
        @Override
        public String call() throws Exception {
            try {
                semaphore.acquire();
                System.out.println(message);
                TimeUnit.SECONDS.sleep(time);
                semaphore.release();
            } catch (InterruptedException e) {

            }
            return message;
        }
    }

    public static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(8, 32, 2, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2 << 6), Executors.defaultThreadFactory());
}
