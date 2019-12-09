package org.mine.quartz;

import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.quartz.job.ConcurrentExcutorJob;
import org.mine.quartz.job.NoConcurrentExcutorJob;
import org.quartz.Job;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Repository;

/**  
 * 定时任务执行基类
 * @Description:
 * @Title:  ExcutorBase.java   
 * @Package org.mine.quartz   
 * @author: wntl    
 * @date:   2019年12月2日 下午10:55:20   
 * @version V1.0 
 * @Copyright: 2019 org.mine.* newspro
 */  
@Repository
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
	
	public static Class<? extends Job> getExcutorJob(String isConccurrent){
		Class<? extends Job> job = null;
		switch (isConccurrent) {
		case JobExcutorEnum.CURR_JOB:
			job = ConcurrentExcutorJob.class;
			break;
		case JobExcutorEnum.NO_CURR_JOB:
			job = NoConcurrentExcutorJob.class;
			break;
		}
		return job;
	}
}
