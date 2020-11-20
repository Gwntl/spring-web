package com.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mine.aplt.enumqz.JobExecutorEnum;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.lock.db.DBLockInfo;
import org.mine.lock.LockWorker;
import org.mine.lock.db.QueueDBOptimisticLocking;
import org.mine.quartz.schduler.AutoScheduler;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/base/application-context-impl.xml"})
public class JdbcConnectToMysqlTest {

//	@Autowired
//	private TuserService service;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void test1(){
		System.out.println("ww");
		String url = "jdbc:mysql://127.0.0.1:3306/msbadb?serverTimezone=UTC";
		String user = "msba";
		String password = "msba";
		try{
			System.out.println("connect ----");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("connect ----");
			Connection connection = DriverManager.getConnection(url, user, password);
			System.out.println("connect ----");
			String sql = "select * from t_user";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			while(set.next()){
				int a = set.getInt(1);
				String b = set.getString(2);
				String c = set.getString(3);
				String d = set.getString(4);
				System.out.println(a + ", " + b + ", " + c + ", " + d);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2(){
//		List<Tuser> list = service.getUsers();
//		System.out.println(list.size());
//		System.out.println(list.get(0).getId() + ", " + list.get(0).getUsername());
//
//		List<String> list1 = service.getNames();
//		System.out.println(list1.size());
//		System.out.println(list1.get(0));
//
//		System.out.println(service.getUser("test").toString());
	}
	
	@Test
	public void test3(){
		MDC.put("trade", "test");
//		List<Tuser> list = service.getAllUsers();
//		System.out.println(list.size());
//		System.out.println(list.get(0).getId() + ", " + list.get(0).getUsername());
//
//		System.out.println(service.isExist("test", "test"));
	}
	
	@Test
	public void test4(){
//		MDC.put("trade", "atest");
//		Tuser tuser = new Tuser();
//		tuser.setUsername("admin");
//		tuser.setPassword("admin");
//		tuser.setRemark("");
//		int num = service.insertOne(tuser);
//		System.out.println(num);
		System.out.println("success");
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
//		BatchRunQueue queue = new BatchRunQueue();
//		Date startTime = null;
//		try {
//			startTime = dateFormat.parse("20191106111021");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		queue.setQueueId("1002");
//		queue.setQueueName("TEST_RUN_1");
//		queue.setQueueCrontrigger("15,55 * * ? * *");
//		queue.setQueueJobId(1002L);
//		queue.setQueueRemark("每分钟的15秒,55秒执行一次");
//		queueDao.insertOne(queue);
//		queueDao.updateOne(queue);
	}
	
	@Test
	public void test5() throws Exception{
//		BatchQueueConf conf = new BatchQueueConf();
//		conf.setQueueId(confCostomDao.getMaxQueueId());
//		conf.setQueueAutoFlag(JobExcutorEnum.AUTO_RUN);
//		conf.setQueueName("测试");
//		queueConfDao.insertOne(conf);
//		
//		BatchJobGroupConf groupConf = new BatchJobGroupConf();
//		groupConf.setJobGroupId(confCostomDao.getJobGroupConfMaxId());
//		groupConf.setJobGroupName("测试组");
//		groupConf.setJobGroupSavelog("0");
//		groupConf.setJobGroupRerun("1");
//		groupConf.setJobGroupNumber(1);
//		groupConf.setJobGroupMaintenanceDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
//		groupConf.setJobGroupInitValue("trade=testGroup");
//		jobGroupConfDao.insertOne(groupConf);
//		
//		BatchQueueConf conf2 = queueConfDao.selectOne1R(conf.getQueueId());
//		conf2.setQueueJobGroupId(groupConf.getJobGroupId());
//		queueConfDao.updateOne1R(conf2);
//		
//		BatchTriggerConf triggerConf = new BatchTriggerConf();
//		triggerConf.setTriggerId(confCostomDao.getTriggerConfMaxId());
//		triggerConf.setTriggerName("测试用触发器");
//		triggerConf.setTriggerCrontrigger("*/5 * * * * ?");
//		triggerConf.setTriggerMaintenanceDate(CommonUtils.dateToString(new Date(), "yyyyMMdd"));
//		triggerConfDao.insertOne(triggerConf);
//		
//		BatchJobGroupConf groupConf2 = jobGroupConfDao.selectOne1R(1L);
//		groupConf2.setJobGroupTriggerId(triggerConf.getTriggerId());
//		jobGroupConfDao.updateOne1R(groupConf2);
		
//		BatchJobDetailConf detailConf = new BatchJobDetailConf();
//		detailConf.setJobdetailId(CommonUtils.addZeroWithMiddle(groupConf2.getJobGroupId(), 
//				confCostomDao.getJobDetailConfMaxId(groupConf2.getJobGroupId())));
//		detailConf.setJobdetailName("测试JOB");
//		detailConf.setJobdetailProvider("");
//		detailConf.setJobdetailActuator("");
//		detailConf.setJobdetailGroupId(groupConf2.getJobGroupId());
//		jobDetailConfDao.insertOne(detailConf);
	}
	@Autowired
	private AutoScheduler schduler;
	
	@Test
	public void test6() throws Exception{
//		schduler.addQueue("continuousDemo01", "queue_demo_01", 0);
//		schduler.addGroup(1L, "test_group", 1L);
//		schduler.addTask("conDemo01", "task_demo_01", new ArrayList<>());
//		schduler.addTask("conDemo02", "task_demo_02", new ArrayList<>());
//		schduler.addTask("conDemo03", "task_demo_03", new ArrayList<>());
//		schduler.addJob("LogJobDemo01", "记录日志作业样例01", "TRIGGER_continuousTest", new ArrayList<>(), "org.mine.quartz.job.QuartzTimingJobExecutor", 0, 0, 0, "");
//		schduler.addJob("LogJobDemo02", "记录日志作业样例02", "TRIGGER_continuousDemo", new ArrayList<>(), "org.mine.quartz.job.QuartzTimingJobExecutor", 0, 0, 0, "");
//		schduler.updateJob("JOB_LogJobDemo01", "", new ArrayList<>(), 0);
//		schduler.addStep("ConFirst", "连续性测试01", "actualAddJob", new ArrayList<>());
//		schduler.addStep("ConSecond", "连续性测试02", "dataPrintServiceImpl", new ArrayList<>());
//		schduler.addStep("ConThird", "连续性测试03", "actualThreeAddJob", new ArrayList<>());
//		List<Long> list = new ArrayList<>();
//		list.add(5L);
//		schduler.orchestrationTask(1L, list);
//		schduler.orchestrationJob(2L, list);
//		schduler.addTrigger("TaskTestDemo", "TaskDemo样例", "", "", "0 */5 * * * ? *", "每5分钟执行一次");
//		BatchTriggerDefinition triggerDefinition = new BatchTriggerDefinition();
//		triggerDefinition.setTriggerId("TRIGGER_continuousTest");
//		triggerDefinition.setTriggerCrontrigger("0 0/4 * * * ? *");
//		triggerDefinition.setTriggerStartTime("");
//		triggerDefinition.setTriggerEndTime("");
//		triggerDefinition.setTriggerRemark("每小时的第一分钟执行");
//		schduler.updateTrigger(triggerDefinition);
//		List<String> list = new ArrayList<>();
//		list.add("STEP_ConFirst");
//		list.add("STEP_ConSecond");
//		list.add("STEP_ConThird");
//		schduler.orchestrationQueue("QUEUE_continuousDemo01", list);
//		schduler.orchestrationJob("JOB_LogJobDemo02", list);
//		schduler.orchestrationJob("JOB_LogJobDemo01", list);


//		schduler.addStep("TaskFirst", "TASK测试01", "firstTask", new ArrayList<>());
//		schduler.addStep("TaskSecond", "TASK测试02", "secondTask", new ArrayList<>());
//		schduler.addStep("TaskThree", "TASK测试03", "threeTask", new ArrayList<>());
//		schduler.addStep("TaskFour", "TASK测试04", "fourTask", new ArrayList<>());
//		schduler.addStep("TaskFive", "TASK测试05", "fiveTask", new ArrayList<>());
//		schduler.addStep("TaskSix", "TASK测试06", "sixTask", new ArrayList<>());


//		schduler.addJob("TaskJobDemo04", "TASK作业样例04", "TRIGGER_TaskTestDemo", new ArrayList<>(), "org.mine.quartz.job.QuartzTimingTaskExecutor", 0, 1, 0, 0, "");
//		schduler.addJob("TaskJobDemo03", "TASK作业样例03", "TRIGGER_TaskTestDemo", new ArrayList<>(), "org.mine.quartz.job.QuartzTimingTaskExecutor", 0, 1, 0, 0, "");
//		schduler.addJob("TaskJobDemo02", "TASK作业样例02", "TRIGGER_TaskTestDemo", new ArrayList<>(), "org.mine.quartz.job.QuartzTimingTaskExecutor", 0, 1, 0, 0, "");

//		schduler.addTask("TaskDemo_1", "Task执行Demo", 0, "TRIGGER_TaskTestDemo", new ArrayList<>(), "org.mine.quartz.job.QuartzTimingTaskExecutor", 0);

		List<String> list = new ArrayList<>();
		list.add("JOB_TaskJobDemo01");
		list.add("JOB_TaskJobDemo02");
		list.add("JOB_TaskJobDemo03");
		list.add("JOB_TaskJobDemo04");
		schduler.orchestrationTask("TASK_TaskDemo_1", list);

//		list.add("STEP_TaskSix");
//		list.add("STEP_TaskFive");
//		list.add("STEP_TaskFour");
//		schduler.orchestrationJob("JOB_TaskJobDemo01", list);
//		schduler.orchestrationJob("JOB_TaskJobDemo03", list);
//		schduler.orchestrationJob("JOB_TaskJobDemo04", list);
	}
	@Test
	public void test7() {
		Map<String, Object> map = GitContext.queryForMap("select * from batch_task_definition", null);
		System.out.println(">>>>>>>>>>>>>>>>>>>" + CommonUtils.toString(map));
//		Long list = GitContext.queryForLong("select count(1) from batch_group_definition");
//		System.out.println(list);
//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//		List<String> list = GitContext.queryForSingleFieldList("select execute_step_id from batch_job_execute where execute_job_id = ? order by execute_step_num",
//				new Object[]{"JOB_LogJobDemo01"}, String.class);
		//EXECUTE_JOB_ID = ? AND
//		List<BatchTaskExecute> execute = GitContext.queryForList(
//				"SELECT EXECUTE_JOB_ID, EXECUTE_JOB_ONE_TIME FROM BATCH_TASK_EXECUTE WHERE EXECUTE_TASK_ID = ? AND " +
//				"VALID_STATUS = '0' ORDER BY EXECUTE_JOB_NUM"
//				, new Object[]{"TASK_TaskDemo_"}, BatchTaskExecute.class);
//		System.out.println(CommonUtils.toString(execute));
		Map<String, Object> successJobMap = GitContext.queryForMap(
				"SELECT JOB_ID, EXECUTION_INSTANCE FROM BATCH_JOB_LOG WHERE ASSOCIATE_TASK_INSTANCE = ? AND JOB_STATUS = ? AND VALID_STATUS = '0'",
				new Object[]{"", JobExecutorEnum.SUCCESS.getValue()}, "JOB_ID", "EXECUTION_INSTANCE");
		System.out.println(CommonUtils.toString(successJobMap));
	}

	@Test
	public void dbLockTest() throws InterruptedException {
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(4, 8, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));
		List<Future> list = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			list.add(poolExecutor.submit(new Worker(i + 1)));
		}
		while(list.size() > 0){
			Iterator<Future> iterator = list.iterator();
			while (iterator.hasNext()) {
				Future future = iterator.next();
				if (future.isDone()) {
					iterator.remove();
				}
			}
		}
	}

	public static class Worker implements Runnable {
		int time;
		public Worker(int time) {
			this.time = time;
		}
		@Override
		public void run() {
			QueueDBOptimisticLocking locking = new QueueDBOptimisticLocking();
            locking.tryLock("QUEUE_TEST", "1", new LockWorker() {
                @Override
                public void worker(DBLockInfo lockInfo) {
                    System.out.println(">>>>>lockInfo : " + lockInfo.toString());
                    int version = lockInfo.getVersion();
                    System.out.println(">>>>>>>>>>>>" + CommonUtils.currentTime(new Date()) + ", " + Thread.currentThread().getName()
                            + " get lock.properties successful. version = " + version);
                    try {
                        TimeUnit.SECONDS.sleep(11);
                    } catch (InterruptedException e) {

                    }
                }
            });

        }
	}
}
