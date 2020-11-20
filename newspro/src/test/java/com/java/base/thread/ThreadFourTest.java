package com.java.base.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadFourTest {
	public static List<String> list = new ArrayList<>(4);
	public static List<List<String>> logs = new ArrayList<>(4);
	
	public static boolean flag = true;
	public static volatile List<String> l = new ArrayList<>(10);
	public static Lock lock = new ReentrantLock();
	public static Condition condition = lock.newCondition();
	
	public static Semaphore semaphore = new Semaphore(1);
	public static SynchronousQueue<String> queue = new SynchronousQueue<>();
	
	public static ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
	public static Object obj = new Object();
	
	public static void main(String[] args){
		
//		System.out.println("begin:"+(System.currentTimeMillis()/1000));
//		/*模拟处理16行日志，下面的代码产生了16个日志对象，当前代码需要运行16秒才能打印完这些日志。
//		修改程序代码，开四个线程让这16个对象在4秒钟打完。
//		*/
//		for(int i=0;i<16;i++){  //这行代码不能改动
//			final String log = ""+(i+1);//这行代码不能改动
//			{
//				//ThreadFourTest.parseLog(log);
//				list.add(log);
//				if(((i + 1) % 4) == 0){
//					logs.add(list);
//					list = new ArrayList<>();
////					list.clear();
//				}
//			}
//		}
//		ThreadFourTest.run();
		
		for(int i=0;i<10;i++){
			new Thread(new Customer()).start();
		}
		
		System.out.println("begin:"+(System.currentTimeMillis()/1000));
		for(int i=0;i<10;i++){  //这行不能改动
			String input = i+"";  //这行不能改动
			//1
//			lock.properties.lock.properties();
//			l.add(input);
//			condition.signal();
//			lock.properties.unlock();
			
			//2
//			try {
//				queue.put(input);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			//3
			try {
				q.put(input);
			} catch (InterruptedException e) {
			}
			
			
			//String output = TestDo.doSome(input);
			//System.out.println(Thread.currentThread().getName()+ ":" + output);
		}
		
	}

	static class Customer implements Runnable{
		@Override
		public void run(){
			try {
				while(true){
					//1
//					lock.properties.lock.properties();
//					if(l.size() <= 0){
//						condition.await();
//					}
//					String output = TestDo.doSome(l.remove(l.size() - 1));
//					System.out.println(Thread.currentThread().getName()+ ":" + output);
					
					//2
//					semaphore.acquire();
//					String output = TestDo.doSome(queue.take());
//					System.out.println(Thread.currentThread().getName()+ ":" + output);
//					semaphore.release();
					
					//3
					String output = TestDo.doSome(q.take());
					System.out.println(Thread.currentThread().getName()+ ":" + output);
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static void run(){
		for(int i = 0; i < 4; i ++){
			final int count = i;
			new Thread(new Runnable(){
				@Override
				public void run(){
					for(int j = 0; j < 4; j ++){
						ThreadFourTest.parseLog(logs.get(count).get(j));
					}
				}
			}).start();	
		}
	}	
	
	// parseLog方法内部的代码不能改动
	public static void parseLog(String log) {
		System.out.println(log + ":" + (System.currentTimeMillis() / 1000));

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class TestDo {
	public static String doSome(String input){
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String output = input + ":"+ (System.currentTimeMillis() / 1000);
		return output;
	}
}
