package org.mine.quartz;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JobExcutorFactory{
	
	private static JobExcutorFactory factory = null;
	
	public static ThreadPoolExecutor executor = null;
	
	private JobExcutorFactory(){}
	
	public static JobExcutorFactory getFactory(){
		if(factory == null){
			synchronized(JobExcutorFactory.class){
				if(factory == null){
					factory = new JobExcutorFactory();
				}
			}
		}
		return factory;
	}
	
	/**
	 * 单例方法, 供Job调度作业时使用.
	 * 根据不同场景实现不同的线程池.
	 * @return
	 */
	public static ThreadPoolExecutor getNewInstance(){
		if(executor == null){
			synchronized (JobExcutorFactory.class) {
				if(executor == null){
					executor = new ThreadPoolExecutor(20, 30, 50L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(20));
				}
			}
		}
		return executor;
	}
	
	public void call(Runnable runnable){
		getNewInstance().execute(runnable);
	}
}
