package org.mine.service.impl.async;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.dao.custom.BatchConfCostomDao;
import org.mine.quartz.schduler.AutoScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Starter {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/base/application-context-impl.xml");
		
		AutoScheduler auToscheduler = (AutoScheduler)context.getBean(AutoScheduler.class);
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GitContext.doIndependentTransActionControl(new BatchOperator() {
			@Override
			public Object call(Map<String, Object> map) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>rescheduleJob begin---------------");
				auToscheduler.rescheduleJob(3L, "0/45 * * * * ?", null, null);
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>rescheduleJob end---------------");
				
				try {
					TimeUnit.SECONDS.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>addJob begin---------------");
				BatchConfCostomDao costomDao = (BatchConfCostomDao)context.getBean(BatchConfCostomDao.class);
				Long queueId = costomDao.getMaxQueueId();
				auToscheduler.addBatchQueue(queueId, "测试Four", 0);
				Long triggerId = costomDao.getTriggerConfMaxId();
				auToscheduler.addTrigger(triggerId, "测试实时增加触发器-2", 
						"", "", "0/25 * * * * ?");
				auToscheduler.addBatchGroup(costomDao.getJobGroupConfMaxId(), "测试组-实时增加-Four", 
						"0", "0", "transName=acutalFourGroup", triggerId, "1", 1, queueId);
				auToscheduler.addScheduleJob(queueId, null, null, triggerId, "org.mine.service.impl.async.ActualAddFourJobImpl");
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>addJob end---------------");
				return null;
			}
		}, null);
	}
}
