package org.mine.service.impl.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.mine.aplt.constant.ApltContanst;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.dao.BatchTimingTaskLogRegisterDao;
import org.mine.dao.custom.BatchDefineCostomDao;
import org.mine.model.BatchTimingTaskLogRegister;
import org.mine.quartz.ExecutorBase;
import org.mine.quartz.job.run.JobTaskCallable;
import org.mine.quartz.schduler.AutoScheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class Starter {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/base/application-context-impl.xml");
//		AutoScheduler auToscheduler = (AutoScheduler)context.getBean(AutoScheduler.class);
//		try {
//			TimeUnit.SECONDS.sleep(15);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		BatchTimingTaskLogRegister register = null;
//		Long id = 198L;
//		while(register == null){
//			register = context.getBean(BatchTimingTaskLogRegisterDao.class).selectOne1R(id, false);
//			if(register != null){
//				try {
//					TimeUnit.SECONDS.sleep(20);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				System.out.println("cancle.........");
//				context.getBean(JobTaskCallable.class).cancel(id);
//				break;
//			}
//			try {
//				TimeUnit.MILLISECONDS.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		
//		GitContext.doIndependentTransActionControl(new BatchOperator() {
//			@Override
//			public Object call(Map<String, Object> map) {
////				System.out.println(">>>>>>>>>>>>>>>>>>>>>>rescheduleJob begin---------------");
////				auToscheduler.rescheduleJob(3L, "0/15 * * * * ?", null, null);
////				System.out.println(">>>>>>>>>>>>>>>>>>>>>>rescheduleJob end---------------");
//				
//				try {
//					TimeUnit.SECONDS.sleep(20);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println(">>>>>>>>>>>>>>>>>>>>>>addJob begin---------------");
//				BatchConfCostomDao costomDao = (BatchConfCostomDao)context.getBean(BatchConfCostomDao.class);
//				Long queueId = costomDao.getMaxQueueId();
//				auToscheduler.addBatchQueue(queueId, "测试Four", 0);
//				Long triggerId = costomDao.getTriggerConfMaxId();
//				auToscheduler.addTrigger(triggerId, "测试实时增加触发器-3", "", "", "0/40 0/2 * * * ?","");
//				
//				Long triggerId1 = costomDao.getTriggerConfMaxId();
//				auToscheduler.addTrigger(triggerId1, "测试实时增加触发器-4", "", "", "20 1/2 * * * ?","");
//				
//				List<String> triggerIds = new ArrayList<>();
//				triggerIds.add(triggerId + "");
//				triggerIds.add(triggerId1 + "");
//				auToscheduler.addBatchGroup(costomDao.getJobGroupConfMaxId(), "测试组-实时增加-Mul", 
//						"0", "org.mine.quartz.job.ConcurrentExcutorJob", "transName=acutalMulGroup", triggerIds, "1", 1, queueId, "每40s运行一次");
//				auToscheduler.addScheduleJob(queueId, null, null, triggerIds, "org.mine.service.impl.async.ActualAddMultiJobImpl");
//				System.out.println(">>>>>>>>>>>>>>>>>>>>>>addJob end---------------");
//				return null;
//			}
//		}, null);
	}
}
