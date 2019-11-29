package com.java.base.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.InitializingBean;

public class GetSynchronizedLock implements InitializingBean{

	public static Map<String, String> lockMaps = new ConcurrentHashMap<String, String>();
	
	public static Lock lock = new ReentrantLock();
	
	public static Condition condition = lock.newCondition();
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try{
						lock.lock();
//						if(lockMaps.size() <= 0){
//							try {
//								condition.await();
//								System.out.println("[" + format.format(new Date()) + " ]等待中------");
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
						
						Iterator<Entry<String, String>> iterator = lockMaps.entrySet().iterator();
						System.out.println("before : " + lockMaps.size());
						TimeUnit.SECONDS.sleep(5);
						System.out.println("after : " + lockMaps.size());
						while (iterator.hasNext()) {
							String flag = iterator.next().getKey();
							System.out.println("[" + format.format(new Date()) + " ] flag - " + flag + ", 监视队列值为: "
									+ lockMaps.remove(flag));
						}
					} catch(Exception e){
						e.printStackTrace();
					} finally{
//						System.out.println("释放锁");
						lock.unlock();
					}
				}
			}
		}, "MONITOR");
		thread.start();
	}
	
	public void init() throws InterruptedException{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 20;
				while(i > 0){
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lockMaps.put("id-" + i, Thread.currentThread().getName() + "--" + i);
					i--;
				}
			}
		}, "TEST-1").start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 20;
				while(i > 0){
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lockMaps.put("id-" + i, Thread.currentThread().getName() + "--" + i);
					i--;
				}
			}
		}, "TEST-2").start();
	}
}
