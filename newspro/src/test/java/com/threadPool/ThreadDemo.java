package com.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo implements Runnable{

	public static int count = 0;
	
	public static String lock = "";
	
	public Lock tranLock = new ReentrantLock();
	
	private static ThreadLocal<ThreadBean> threadBean = new ThreadLocal<ThreadBean>(){
		protected ThreadBean initialValue() {
			ThreadBean01 bean = new ThreadBean01();
			return bean;
		};
	};
//	private static ThreadLocal<ThreadBean> threadBean = new ThreadLocal<ThreadBean>();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		synchronized (lock.properties) {
//			count++;
//			System.out.println(Thread.currentThread().getName() + ", " + count);
//		}
		ThreadBean01 bean01 = (ThreadBean01) threadBean.get();
		if(bean01 == null){
			bean01 = new ThreadBean01();
			threadBean.set(bean01);
		}
		System.out.println(bean01.toString());
		tranLock.lock();
		for(int i = 0; i < 3; i++){
			count++;
			bean01.setId(count + "" + i);
			bean01.setNumStr(count + bean01.getNumStr() + i);
			System.out.println(Thread.currentThread().getName() + ", " + count + ", " + threadBean.get().toString());
		}
		tranLock.unlock();
	}

	public static void main(String[] args) {
		ThreadDemo demo = new ThreadDemo();
//		Thread thread = new Thread(demo, "c");
//		Thread thread1 = new Thread(demo, "c1");
//		Thread thread2 = new Thread(demo, "c2");
//		thread.start();
//		thread1.start();
//		thread2.start();
		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.submit(demo);
		executor.submit(demo);
		executor.submit(demo);
		executor.shutdown();
		System.out.println("12121");
	}
}
