package org.mine.quartz;

import org.mine.quartz.job.ConcurrentExcutorJob;
import org.mine.quartz.job.NoConcurrentExcutorJob;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Repository;

public class ExcutorBase implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ExcutorBase.applicationContext = applicationContext;
	}

	/**
	 * 获取Spring容器中的SchedulerFactoryBean对象,以便于获取Scheduler对象
	 */
	public static SchedulerFactoryBean getSchedulerFactoryBean() {
		return (SchedulerFactoryBean) applicationContext.getBean("&quartzSch_bean");
	}
	
	public static Class getScheduleJobClass(String curr){
		Class clazz = null;
		switch (curr) {
		case "1":
			clazz = NoConcurrentExcutorJob.class;
			break;

		default:
			clazz = ConcurrentExcutorJob.class;
			break;
		}
		return clazz;
	}
}
