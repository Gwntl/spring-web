package org.mine.quartz;

import org.mine.aplt.exception.GitWebException;
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
 * @Package org.mine.batch
 * @author: wntl    
 * @date:   2019年12月2日 下午10:55:20   
 * @version V1.0 
 * @Copyright: 2019 org.mine.* newspro
 */  
@Repository
public class ExecutorBase implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ExecutorBase.applicationContext = applicationContext;
	}

	/**
	 * 获取Spring容器中的SchedulerFactoryBean对象,以便于获取Scheduler对象
	 */
	public static SchedulerFactoryBean getSchedulerFactoryBean() {
		return (SchedulerFactoryBean) applicationContext.getBean("&DefaultQuartzScheduler");
	}
	
	public static SchedulerFactoryBean getDynamicScheduler() {
		return (SchedulerFactoryBean) applicationContext.getBean("&DynamicOperationScheduler");
	}
	
	public static Class<? extends Job> getExecutorJob(String jobPath) {
		try {
			return (Class<? extends Job>) Class.forName(jobPath);
		} catch (ClassNotFoundException e) {
			throw GitWebException.GIT1001(jobPath + "不存在");
		}
	}
}
