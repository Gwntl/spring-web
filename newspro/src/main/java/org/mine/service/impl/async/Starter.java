package org.mine.service.impl.async;

import org.mine.aplt.constant.JobConstant;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.util.CommonUtils;
import org.mine.quartz.dto.JobOperatorDto;
import org.mine.quartz.run.job.JobNoRecodeLogLogic;
import org.mine.quartz.run.job.JobRecodeLogLogic;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Starter {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/base/application-context-impl.xml");

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = dateFormat.parse("2020-09-29 17:25:00");
			long nanoTime = date.getTime();
			long currentNanoTime = 0L;
			while ((currentNanoTime = System.currentTimeMillis()) < nanoTime) {
				//do nothing
			}
//			System.out.println("开始执行逻辑.");
//
//			TimeUnit.SECONDS.sleep(53);
////		String ti = TaskRecodeLogLogic.getExecutingTaskInstance();
////		System.out.println("待暂停的实例: " + ti);
//			String taskInstance = "EXECUTOR_TASK_0000000104";
//			String taskID = "TASK_TaskDemo_1";
//			TaskRecodeLogLogic.stopTask(taskInstance);
//
			TimeUnit.MILLISECONDS.sleep(15100);
//			String jobID = "JOB_LogJobDemo01";
//			BatchJobDefinition jobDefinition = GitContext.getBean(BatchJobDefinitionDao.class).selectOne1R(jobID,true);
//			ExecuteTaskDto taskDto = new ExecuteTaskDto();
//			taskDto.setJobId(jobID);
//			taskDto.setJobName(jobDefinition.getJobName());
//			taskDto.setJobLogFlag(jobDefinition.getJobLogFlag());
//			taskDto.setJobSkipFlag(jobDefinition.getJobSkipFlag());
//			taskDto.setJobRunMutiFlag(jobDefinition.getJobRunMutiFlag());
//			taskDto.setJobTimeDelayFlag(jobDefinition.getJobTimeDelayFlag());
//			taskDto.setJobTimeDelayValue(jobDefinition.getJobTimeDelayValue());
//			CommonUtils.initMapValue(taskDto.getJobInitValue(), jobDefinition.getJobInitValue());
//			new JobRecodeLogLogic(taskDto).restartJob("EXECUTOR_JOB_0000000364");
//			new JobNoRecodeLogLogic(taskDto).restartJob("EXECUTOR_JOB_0000000371");
//			TaskRecodeLogLogic.restartTask(taskInstance, taskID);
			JobNoRecodeLogLogic jobNoRecodeLogLogic = context.getBean(JobNoRecodeLogLogic.class);
//			JobNoRecodeLogLogic jobRecodeLogLogic = new JobNoRecodeLogLogic();
            String instance = "EXECUTOR_JOB_0000000399";
			JobOperatorDto dto = new JobOperatorDto();
			dto = jobNoRecodeLogLogic.cancelJob(instance);
			int retry = 9;
			while (true) {
				if (!dto.getFlag() && CommonUtils.equals(dto.getInfo(), JobConstant.JOB_UNINITIALIZED)) {
					if (retry < 1) {
						System.out.println("已超重试次数.");
						break;
					}
					System.out.println(dto.toString() + ", try again once.");
					try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) {}
					dto = jobNoRecodeLogLogic.cancelJob(instance);
					retry--;
				} else {
					break;
				}
			}

//			JobRecodeLogLogic recodeLogLogic = context.getBean(JobRecodeLogLogic.class);
//			recodeLogLogic.stopJob(instance);
//			while (true) {
//				//当执行取消操作时,程序运行中正好处于调度JOB前的数据处理逻辑中, 则等待调度开始再执行取消操作.
//				if (jobRecodeLogLogic.cancelJob(instance)) {
//					break;
//				}
//				try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) {}
//			}

        } catch (InterruptedException | ParseException e) {
		    e.printStackTrace();
		} catch (Exception e) {
            System.out.println("error : " + MineException.getStackTrace(e));
        }
//		String taskInstance = "EXECUTOR_TASK_0000000103";
//		String taskID = "TASK_TaskDemo_1";
//		TaskRecodeLogLogic.restartTask(taskInstance, taskID);


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
//						"0", "org.mine.batch.job.ConcurrentExcutorJob", "transName=acutalMulGroup", triggerIds, "1", 1, queueId, "每40s运行一次");
//				auToscheduler.addScheduleJob(queueId, null, null, triggerIds, "org.mine.service.impl.async.ActualAddMultiJobImpl");
//				System.out.println(">>>>>>>>>>>>>>>>>>>>>>addJob end---------------");
//				return null;
//			}
//		}, null);
	}
}
