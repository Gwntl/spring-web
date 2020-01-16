package com.java.base.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLock {

	private Lock lock = new ReentrantLock();
	private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1);
	
	public Lock getLock() {
		return lock;
	}
	public LinkedBlockingQueue<String> getQueue() {
		return queue;
	}
	public static void main(String[] args) {
		
		final ThreadLock threadLock = new ThreadLock();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try{
						System.out.println("--------");
						String s = threadLock.getQueue().take();
						System.out.println(s);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		System.out.println("===============");
		
		try{
			System.out.println("=======tryLock========");
			if(threadLock.getLock().tryLock()){
				System.out.println("========getLock=======");
				if(threadLock.getQueue().size() <= 0){
					threadLock.getQueue().offer("1", 1, TimeUnit.SECONDS);
				}
				threadLock.getLock().unlock();
			} else{
				System.out.println("=======fail=======");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("========end======");
	}
}
