package com.threadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CountDownTest {
	
	public static void main(String[] args) {
		try {
//			CountDownLatch startSignal = new CountDownLatch(1);
//			CountDownLatch endSignal = new CountDownLatch(10);
			//每次准许执行的线程数
			Semaphore semaphore = new Semaphore(1);
//			for(int i = 0; i < 10; i++){
//				new Thread(new Worker(startSignal, endSignal), "THREAD_" + i).start();
//			}
			
			for(int i = 0; i < 10; i++){
				new Thread(new WorkerSem(semaphore), "THREAD_" + i).start();
			}
			
//			System.out.println("线程执行前处理");
//			startSignal.countDown();
//			System.out.println("start-----");
			//等待指定线程数执行完毕
//			endSignal.await();
//			System.out.println("线程执行完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class Worker implements Runnable {
		CountDownLatch startSignal ;
		CountDownLatch endSignal;
		public Worker(CountDownLatch startSignal, CountDownLatch endSignal) {
			this.startSignal = startSignal;
			this.endSignal = endSignal;
		}
		
		@Override
		public void run() {
			try {
				//等待执行信号
				startSignal.await();
				TimeUnit.SECONDS.sleep(2);
				System.out.println(Thread.currentThread().getName());
				//每执行完一个线程, 信号量值减一.
				endSignal.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class WorkerSem implements Runnable{
		Semaphore semaphore;
		public WorkerSem(Semaphore semaphore) {
			this.semaphore = semaphore;
		}
		@Override
		public void run() {
			try {
				semaphore.acquire();
				TimeUnit.SECONDS.sleep(2);
				System.out.println(Thread.currentThread().getName());
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
