package com.java.base.thread;

import java.lang.Thread.State;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptedThreadTest {

	private Thread thread = null;
	
	private BlockingQueue<String> queue = new LinkedBlockingQueue<>(1 << 4);
	
	private static volatile boolean interrupt = false;
	
	private Lock lock = new ReentrantLock();
	
	/**
	 * @return the thread
	 */
	public Thread getThread() {
		return thread;
	}

	/**
	 * @param thread the thread to set
	 */
	public void setThread(Thread thread) {
		this.thread = thread;
	}

	/**
	 * @return the queue
	 */
	public BlockingQueue<String> getQueue() {
		return queue;
	}

	/**
	 * @param queue the queue to set
	 */
	public void setQueue(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	public static void main(String[] args) {
		InterruptedThreadTest threadTest = new InterruptedThreadTest();
		threadTest.startThread();
		
		try {
			for(int i = 0; i < 5; i++){
				threadTest.getQueue().put("13646--" + i);
			}
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			
		}
		threadTest.stop();
		
		try {
			for(int i = 0; i < 5; i++){
				threadTest.getQueue().put("a13646--" + i);
			}
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			
		}
		System.out.println(threadTest.getThread().isInterrupted());
		System.out.println(Thread.activeCount());
		System.out.println(threadTest.getThread().getState());
		System.out.println(threadTest.getThread().isAlive());
		System.out.println(threadTest.getThread().getName());
		System.out.println(threadTest.getThread().getClass());
		
		threadTest.restart();
	}
	
	public void startThread(){
		if(thread == null){
			thread = new Thread(new Interr());
			thread.start();
		}
	}
	
	public void stop(){
		try{
			lock.lock();
			interrupt = true;
			thread.interrupt();
		} finally{
			lock.unlock();
		}
	}
	
	public void restart(){
		try{
			lock.lock();
			if(thread.getState() == State.TERMINATED){
				thread = new Thread(new Interr());
				thread.start();
			}
		} finally{
			lock.unlock();
		}
	}
	
	class Interr implements Runnable{

		@Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				try{
					String s = queue.take();
					System.out.println(s);
				} catch(InterruptedException e){
					if(interrupt){
						System.out.println("批量线程已被停止,若需要请重新启动.....");
						return;
					}
				}
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					System.out.println("[s]批量线程已被停止,若需要请重新启动.....");
					thread.interrupt();
				}
			}
			
		}
		
	}
}