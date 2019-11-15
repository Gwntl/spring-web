package com.java.base.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class DeamonThreadTest {

	protected static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
	
	private static Object lock = new Object();
	
	public DeamonThreadTest() {
		// TODO Auto-generated constructor stub
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				while(true){
//					if(blockingQueue.size() <= 0){
//						try {
//							synchronized(lock){
//								lock.wait(1000L);
//							}
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					} else {
//						System.out.println(Thread.currentThread().getName() + "11111");
//						System.out.println(blockingQueue.size());
//						String message = blockingQueue.poll();
//						if(message != null){
//							System.out.println(blockingQueue.size());
//							System.out.println(Thread.currentThread().getName() + ", " +message + ">>>>>>>>");
//						}
//					}
//				}
//			}
//		}).start();
	}
	
	public static void main(String[] args) throws InterruptedException {
		DeamonThreadTest test = new DeamonThreadTest();
		test.startDeamon();
//		test.startDeamon();
//		new DeamonThreadTest().notifyLock();
//		Put put = new Put("s");
//		Put put1 = new Put("ssss");
//		Thread thread = new Thread(put);
//		Thread thread1 = new Thread(put1);
		Service service = new Service();
		ThreadA a = new ThreadA(service);
		ThreadB b = new ThreadB(service);
		TimeUnit.SECONDS.sleep(5L);
		System.out.println(Thread.currentThread().getName() + "塞值1");
		new Thread(a).start();
		new Thread(b).start();
//		thread.start();
//		thread1.start();
		TimeUnit.SECONDS.sleep(5L);
//		System.out.println(Thread.currentThread().getName() + "塞值2");
//		thread1.start();
		
		synchronized(blockingQueue){
			System.out.println("执行同步块");
		}
	}
	
	public void notifyLock()throws InterruptedException{
		System.out.println(Thread.currentThread().getName() + "111111");
		TimeUnit.SECONDS.sleep(5L);
		System.out.println(Thread.currentThread().getName() + "塞值1");
		blockingQueue.put("testOne");
		synchronized(lock){
			lock.notify();
		}
		TimeUnit.SECONDS.sleep(5L);
		System.out.println(Thread.currentThread().getName() + "塞值2");
		blockingQueue.put("testTwo");
		synchronized(lock){
			lock.notify();
		}
		TimeUnit.SECONDS.sleep(1L);
		System.out.println(Thread.currentThread().getName() + "end");
	}
	
	public void startDeamon(){
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					if(blockingQueue.size() > 0){
						String message = blockingQueue.poll();
						System.out.println(Thread.currentThread().getName() + ", " + message + ">>>>>>>>");
					}
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	static class Put implements Runnable{

		private String message;
		
		public Put(String message) {
			this.message = message;
		}
		
		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + "塞值1111");
				for(int i = 0; i < 10; i++){
					blockingQueue.put(message);
				}
				System.out.println("队列长度: " + blockingQueue.size());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
