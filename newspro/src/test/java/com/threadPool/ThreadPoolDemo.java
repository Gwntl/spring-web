package com.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ThreadPoolDemo implements Callable<String>{

	public static int count = 0;
	private static Lock lock = new ReentrantLock();
	
	@Override
	public String call() throws Exception {
		try{
//			if(lock.tryLock()){
//				count++;
//				System.out.println("1111111," + count);
//			}
			lock.lock();
			count++;
			System.out.println("1111111," + count);
			return "success" + count;
		}catch(Exception e){
			throw new RuntimeException();
		} finally{
			lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		List<Future<String>> futures = new ArrayList<Future<String>>();
		ThreadPoolDemo demo = new ThreadPoolDemo();
//		int threadNum = 3;
//		ExecutorService service = Executors.newFixedThreadPool(threadNum);
//		for (int i = 0; i < threadNum; i++) {
//			futures.add(service.submit(demo));
//		}
//		if(service.isTerminated()){
//			service.shutdown();
//		}
//		System.out.println(futures.get(0).get());
//		System.out.println(futures.get(1).get());
//		System.out.println(futures.get(2).get());
//		List<Callable<String>> tasks = new ArrayList<>();
//		tasks.add(demo);
//		tasks.add(demo);
//		tasks.add(demo);
//		futures = service.invokeAll(tasks);
//		System.out.println(futures.get(0).get());
//		System.out.println(futures.get(1).get());
//		System.out.println(futures.get(2).get());
		
		/*
		 * corePoolSize:指定了线程池中的线程数量,它的数量决定了添加的任务是开辟新的线程去执行,还是放到workQueue任务队列中去.
		 * maximumPoolSize:指定了线程池中的最大线程数量,这个参数会根据你使用的workQueue任务队列的类型,决定线程池会开辟的最大线程数量.
		 * keepAliveTime:当线程池中空闲线程数量超过corePoolSize时,多余的线程会在多长时间内被销毁.
		 * unit:keepAliveTime的单位.
		 * workQueue:任务队列,被添加到线程池中,但尚未被执行的任务;它一般分为直接提交队列、有界任务队列、无界任务队列、优先任务队列几种.
		 * 			 直接提交队列：设置为SynchronousQueue队列，SynchronousQueue是一个特殊的BlockingQueue，它没有容量，没执行一个插入操作就会阻塞，
		 * 						    需要再执行一个删除操作才会被唤醒，反之每一个删除操作也都要等待对应的插入操作。
		 * 			 有界的任务队列：有界的任务队列可以使用ArrayBlockingQueue实现.
		 * 			 无界的任务队列：有界任务队列可以使用LinkedBlockingQueue实现.
		 * 
		 * 
		 * threadFactory:线程工厂,用于创建线程,一般用默认即可.
		 * handler:拒绝策略,当任务太多来不及处理时,如何拒绝任务. AbortPolic策略，直接抛出异常
		 * */
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10)
//				, Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
//		Future<String> future = null;
//		for(int i = 0; i < 20; i ++){
//			future = executor.submit(demo);
//			System.out.println(future.get());
//		}
//		executor.shutdown();
		
		ThreadPoolExecutor executor1 = new ThreadPoolExecutor(10, 20, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10)
				, Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
		FutureTask<String> futureTask = new FutureTask<>(demo);
		for(int i = 0; i < 20; i ++){
			executor1.execute(futureTask);
			System.out.println(">>>>>>>>>> : " + futureTask.get());
		}
		executor1.shutdown();
		
//		//异步操作
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 10000, TimeUnit.SECONDS, 
//				new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
//		//获取有结果返回的异步线程结果
//		CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
//			@Override
//			public String get() {
//				// TODO Auto-generated method stub
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println("callback");
//				return "12u383637";
//			}
//		}, executor);
//		//获取有结果返回的异步线程结果
//		future.thenAcceptAsync(new Consumer<String>() {
//
//			@Override
//			public void accept(String t) {
//				
//				System.out.println(t);
//			}
//		});
//		
//		//获取无结果返回的线程是否执行结束
//		CompletableFuture<Void> future1 = CompletableFuture.runAsync(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					Thread.sleep(4000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println("线程执行结束");
//			}
//		}, executor);
//		//获取无结果返回的线程是否执行结束
//		future1.thenAcceptAsync(new Consumer<Void>() {
//
//			@Override
//			public void accept(Void t) {
//				// TODO Auto-generated method stub
//				System.out.println("success");
//			}
//		});
//		
//		
//		executor.shutdown();
//		System.out.println("start");
		
//		Lable:
//			for(int i = 0; i < 10; i++){
//				System.out.println(i + ", >>>>>>");
//				if(i == 5){
//					break Lable;
//				}
//				for(int j = 0; j < i + 1; j ++){
//					System.out.println(j + ", ......");
//					if(j == 2){
//						System.out.println("continue");
//						continue Lable;
//					}
//					System.out.println(">>>>>>>>>>>>>>>>end>>>>>>>>>>>>>");
//				}
//				System.out.println("for one end");
//			}
//		System.out.println("end");
	}
	
}
