package org.mine.quartz;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JobExcutorFactory{
	
	private static JobExcutorFactory factory = null;
	
	public volatile static ThreadPoolExecutor executor = null;
	
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
	 * 单例方法(双重检验锁,依赖volatile的内存可见性及先写后读及重量级锁锁类实例.), 供Job调度作业时使用.
	 * 根据不同场景实现不同的线程池.
	 * @return
	 */
	public static ThreadPoolExecutor getNewInstance(){
		if(executor == null){
			synchronized (JobExcutorFactory.class) {
				if(executor == null){
					executor = new ThreadPoolExecutor(20, 30, 50L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(20), new CustomThreadFactory("job-executor-"));
				}
			}
		}
		return executor;
	}
	
	/**
	 * @Description:  自定义Thread创建工厂. 
	 * @ClassName: CustomThreadFactory
	 * @author: wntl
	 * @date: 2020年5月27日 下午2:55:31
	 */
	public static class CustomThreadFactory implements ThreadFactory {
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		public CustomThreadFactory(String threadNamePre) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = threadNamePre + "pool-" + poolNumber.getAndIncrement() + "-thread-";
		}

		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}
	
	public void call(Runnable runnable){
		getNewInstance().execute(runnable);
	}
}
