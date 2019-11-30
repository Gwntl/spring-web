package com.jdbc;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mine.aplt.enumqz.JobExcutorEnum;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.mine.controller.login.RegisterUserController;
import org.mine.dao.BatchJobDetailConfDao;
import org.mine.dao.BatchJobGroupConfDao;
import org.mine.dao.BatchQueueConfDao;
import org.mine.dao.BatchRunJobDetailDao;
import org.mine.dao.BatchRunQueueDao;
import org.mine.dao.BatchTriggerConfDao;
import org.mine.dao.custom.BatchConfCostomDao;
import org.mine.dao.custom.impl.BatchConfCostomDaoImpl;
import org.mine.model.BatchJobDetailConf;
import org.mine.model.BatchJobGroupConf;
import org.mine.model.BatchQueueConf;
import org.mine.model.BatchRunJobDetail;
import org.mine.model.BatchRunQueue;
import org.mine.model.BatchTriggerConf;
import org.mine.model.Tuser;
import org.mine.service.BatchRunQueueService;
import org.mine.service.TuserService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/base/application-context-impl.xml"})
public class JdbcConnectToMysqlTest {

	@Autowired
	private TuserService service;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void test1(){
		System.out.println("ww'");
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
		List<Tuser> list = service.getUsers();
		System.out.println(list.size());
		System.out.println(list.get(0).getId() + ", " + list.get(0).getUsername());
		
		List<String> list1 = service.getNames();
		System.out.println(list1.size());
		System.out.println(list1.get(0));
		
		System.out.println(service.getUser("test").toString());
	}
	
	@Test
	public void test3(){
		MDC.put("trade", "test");
		List<Tuser> list = service.getAllUsers();
		System.out.println(list.size());
		System.out.println(list.get(0).getId() + ", " + list.get(0).getUsername());
		
		System.out.println(service.isExist("test", "test"));
	}
	
	@Autowired
	private BatchRunQueueDao queueDao;
	
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
		BatchRunQueue queue = new BatchRunQueue();
//		Date startTime = null;
//		try {
//			startTime = dateFormat.parse("20191106111021");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		queue.setQueueId("1002");
		queue.setQueueName("TEST_RUN_1");
		queue.setQueueCrontrigger("15,55 * * ? * *");
//		queue.setQueueJobId(1002L);
		queue.setQueueRemark("每分钟的15秒,55秒执行一次");
//		queueDao.insertOne(queue);
		queueDao.updateOne(queue);
	}
	
	@Autowired
	private BatchRunJobDetailDao jobDetailDao;
	@Autowired
	private RegisterUserController register;
	@Autowired
	private BatchQueueConfDao queueConfDao;
	@Autowired
	private BatchConfCostomDao confCostomDao;
	@Autowired
	private BatchTriggerConfDao triggerConfDao;
	@Autowired
	private BatchJobGroupConfDao jobGroupConfDao;
	@Autowired
	private BatchJobDetailConfDao jobDetailConfDao;
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
		
		BatchJobDetailConf detailConf = jobDetailConfDao.selectOne1R(1001L);
		detailConf.setJobdetailProvider("org.mine.service.impl.DataPrintServiceImpl");
		jobDetailConfDao.updateOne1R(detailConf);
	}
}
