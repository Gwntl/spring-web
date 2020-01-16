package com.java.base.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.mine.aplt.util.CommonUtils;

public class ThreadMain2 {
	
	private static List<FutureTask<String>> futureTasks = Collections.synchronizedList(new ArrayList<>());
	
	public static void main(String[] args) {
		System.out.println("begin: " + CommonUtils.dateToString(new Date(), "yyyyMMdd HH:mm:ss"));
		ExecutorService executor = Executors.newCachedThreadPool();
		for(int i = 0; i < 3; i++){
			FutureTask<String> task = new FutureTask<>(new ThreadMain2.CallableBlock());
			executor.submit(task);
			futureTasks.add(task);
		}
		System.out.println("middle: " + CommonUtils.dateToString(new Date(), "yyyyMMdd HH:mm:ss"));
		
		new Thread(new Runnable() {
			public void run() {
				if(futureTasks != null && futureTasks.size() > 0){
					for(int i = 0; i < futureTasks.size(); i++){
						if(i == 0){
							try{
								TimeUnit.SECONDS.sleep(1);
							} catch(InterruptedException e){
								e.printStackTrace();
							}
						}
						futureTasks.get(i).cancel(true);
					}
				}
			}
		}).start();
		Iterator<FutureTask<String>> iterator = futureTasks.iterator();
		while(iterator.hasNext()){
			FutureTask<String> t = iterator.next();
			try {
				if(t.isDone()){
					if(t.isCancelled()){
						System.out.println(CommonUtils.dateToString(new Date(), "yyyyMMdd HH:mm:ss") + "---> cancel");
					} else {
						String s = t.get(1, TimeUnit.SECONDS);
						System.out.println(CommonUtils.dateToString(new Date(), "yyyyMMdd HH:mm:ss") + "---> " + s);
					}
					break;
				}
			} catch (InterruptedException e) {
				System.out.println("InterruptedException.....");
			} catch (ExecutionException e) {
				System.out.println("ExecutionException.....");
			} catch (TimeoutException e) {
				System.out.println("TimeoutException.....");
			} catch(CancellationException e){
				System.out.println("CancellationException.....");
			}
		}
		System.out.println("end: " + CommonUtils.dateToString(new Date(), "yyyyMMdd HH:mm:ss"));
	}
	
	static class CallableBlock implements Callable<String>{
		@Override
		public String call() throws Exception {
			int i = 5;
			while(true){
				System.out.println(i);
				if(i == 5){
					System.out.println("-----run exception----");
					throw new RuntimeException("-----run exception----");
				}
				if(i <= 0){
					break;
				}
				i--;
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					System.out.println(CommonUtils.dateToString(new Date(), "yyyyMMdd HH:mm:ss") + "---> 被打断了.....");
					throw new RuntimeException();
				}
			}
			return "3646";
		}
		
	}
	
}
