package com.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CannelThreadPool {

	static class Worker implements Callable<String> {

		AtomicBoolean executorFlag = new AtomicBoolean(false);
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		
		public AtomicBoolean getExecutorFlag() {
			return executorFlag;
		}
		
		void pause(){
			try {
				lock.lock();
				condition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally{
				lock.unlock();
			}
		}
		
		void restart(){
			executorFlag.set(false);
			try {
				lock.lock();
				condition.signal();
			} finally{
				lock.unlock();
			}
		}
		
		@Override
		public String call() throws Exception {
			System.out.println(System.currentTimeMillis() + "  >>>业务开始>>");
			while(true){
				try{
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e){
					throw new NullPointerException("任务已被取消......");
				}
				System.out.println(System.currentTimeMillis() + "  >>>3333>>");
				if(executorFlag.get()){
					System.out.println(System.currentTimeMillis() + "  >>>线程被暂停....>>");
					//暂停线程
					pause();
					System.out.println(System.currentTimeMillis() + "  >>>线程被重启....>>");
				}
				break;
			}
			System.out.println(System.currentTimeMillis() + "  >>>业务以已经完成>>");
			return "success";
		}
	}
	
	public static void main(String[] args) {
		System.out.println("11111");
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
		Worker worker = new Worker();
		FutureTask<String> task = new FutureTask<>(worker);
		executor.execute(task);
		
		System.out.println("22222");
		while(true){
			if(task.isDone()){
				try{
					if(task.isCancelled()){
						System.out.println("被取消分支......");
					} else {
						System.out.println("8888888");
						String result = task.get(1, TimeUnit.SECONDS);
						System.out.println(result);
					}
				} catch (InterruptedException | ExecutionException | TimeoutException e){
					e.printStackTrace();
				}
				
				break;
			} else {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				System.out.println(System.currentTimeMillis() + " >>>>执行中");
//				System.out.println(System.currentTimeMillis() + " >>>>开始取消");
//				task.cancel(true);
//				System.out.println(System.currentTimeMillis() + " >>>>开始完毕");
//				System.out.println(System.currentTimeMillis() + " >>>>是否取消成功 : " + task.isCancelled());
				//暂停
				worker.getExecutorFlag().set(true);
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				worker.restart();
			}
		}
		executor.shutdown();
	}
}
