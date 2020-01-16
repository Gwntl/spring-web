package com.java.base.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class ThreadMain3 {

	public static BlockingQueue<String> queue = new SynchronousQueue<String>();
	
	public static void main(String[] args) {
		for(int i= 0; i< 10; i ++){
			new Thread(new InnerThread(), "INNERTHREAD_" + i).start();
		}
		try {
			TimeUnit.SECONDS.sleep(10);
			queue.put("1112");
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + ", " + "_failed");
		}
	}
	
	static class InnerThread implements Runnable {
		@Override
		public void run() {
			while(true){
				try {
					System.out.println(Thread.currentThread().getName() + " : " + queue.take());
				} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().getName() + ", " + "_failed");
				}
			}
		}
	}
}
