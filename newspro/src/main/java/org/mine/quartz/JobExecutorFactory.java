package org.mine.quartz;

import org.mine.aplt.constant.ApltContanst;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JobExecutorFactory {
	
	private volatile static JobExecutorFactory factory = null;
	
	public volatile static ThreadPoolExecutor executor = null;
	
	public volatile static ThreadPoolExecutor executorNoLog = null;
	
	private JobExecutorFactory(){}

	public static JobExecutorFactory getFactory() {
		if (factory == null) {
			synchronized (JobExecutorFactory.class) {
				if (factory == null) {
					factory = new JobExecutorFactory();
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
	private static ThreadPoolExecutor getNewInstance() {
		if (executor == null) {
			synchronized (JobExecutorFactory.class) {
				if (executor == null) {
					//此处使用有界队列, 由于需要记录日志的异步定时任务偏少.
					executor = new ThreadPoolExecutor(16, 32, 50L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1024), new CustomThreadFactory("JOB-EXECUTOR-"));
				}
			}
		}
		return executor;
	}

	private static ThreadPoolExecutor getNoLogInstance() {
		if (executorNoLog == null) {
			synchronized (JobExecutorFactory.class) {
				if (executorNoLog == null) {
					//高并发执行任务时间短.
					executorNoLog = new ThreadPoolExecutor(ApltContanst.CPU_COUNT << 1, ApltContanst.CPU_COUNT << 1,
							1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(4096), new CustomThreadFactory("NO-LOG-JOB-EXECUTOR-"));
				}
			}
		}
		return executorNoLog;
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
			namePrefix = threadNamePre + "POOL-" + poolNumber.getAndIncrement() + "-THREAD-";
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
	
	public static void call(Runnable runnable, int logFlag) {
		if (logFlag == 0)
			getNewInstance().execute(runnable);

		if (logFlag == 1)
			getNoLogInstance().execute(runnable);
	}
}
