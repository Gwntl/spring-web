package com.java.base.thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.mine.aplt.util.CommonUtils;
import org.slf4j.MDC;

public class ThreadMain {
	private static volatile boolean exitFlag = false;
	
	private static Object obj = new Object();
	
	public static void main(String[] args) {
//		Service service = new Service();
//		
//		ThreadA a = new ThreadA(service);
//		ThreadB b = new ThreadB(service);
//		new Thread(a).start();
//		new Thread(b).start();
		
//		System.out.println(Integer.MAX_VALUE >> 16);
		MDC.put("trade", "Y_TE");
		try{
			Thread thread = new Thread(){

				@Override
				public void run() {
					super.run();
					int i = 0;
					while(!isInterrupted()){
						try{
							System.out.println(i++);
							TimeUnit.SECONDS.sleep(2);
						}catch(InterruptedException e){
							System.out.println("糟了,睡着了");
							interrupt();
							break;
						}
					}
//					try {
//						TimeUnit.SECONDS.sleep(10);
//						System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + ": " + Thread.currentThread().getName() + "执行完成-----");
//					} catch (InterruptedException e) {
//						// TODO: handle exception
//					}
					
				}
			};
			Thread aa = new Thread(new ThreadAA(thread));
			thread.start();
			aa.start();
			TimeUnit.SECONDS.sleep(5);
			System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + ": " + "thread等待中.....");
			synchronized (thread) {
				thread.wait();
			}
			TimeUnit.SECONDS.sleep(3);
			System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + ": " + "thread被唤醒了.....");
//			System.out.println("sleep结束,等待线程完成中.......");
//			thread.join();
//			System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + ": " + Thread.currentThread().getName() + "执行完成-----");
			thread.interrupt();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public static class ThreadAA implements Runnable{
		Thread thread;
		public ThreadAA(Thread thread) {
			this.thread = thread;
		}
		@Override
		public void run() {
			try{
				TimeUnit.SECONDS.sleep(10);
				System.out.println("------");
				synchronized (thread) {
					thread.notifyAll();
				}
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
}
