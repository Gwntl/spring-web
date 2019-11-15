package org.mine.quartz.job;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JobExcutorAble{
	
	public volatile static ThreadPoolExecutor executor = null;
	
	/**
	 * 单例方法, 供Job调度作业时使用
	 * @return
	 */
	public static ThreadPoolExecutor getNewInstance(){
		if(executor == null){
			synchronized (JobExcutorAble.class) {
				if(executor == null){
					executor = new ThreadPoolExecutor(20, 30, 50L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));
				}
			}
		}
		return executor;
	}
	
	public <T> T call(Callable<T> callable){
		return (T) getNewInstance().submit(callable);
	}
}
