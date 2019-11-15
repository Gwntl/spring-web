package com.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchTriggerConfDao;
import org.mine.model.BatchTriggerConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/base/application-context-impl.xml"})
public class CodeGeneratorTest {

	@Autowired
	private BatchTriggerConfDao confDao;
	
	@Test
	public void test() {
//		BatchTriggerConf triggerConf = new BatchTriggerConf();
//		triggerConf.setTriggerId(1000L);
//		triggerConf.setTriggerName("测试1");
//		triggerConf.setTriggerCrontrigger("*/5 * * * * ?");
//		triggerConf.setTriggerJobGroupId(5001L);
//		triggerConf.setTriggerMaintenanceDate("20191114");
//		triggerConf.setVaildStatus("0");
//		triggerConf.setTriggerRemark("每5秒执行一次");
//		confDao.insertOne(triggerConf);
		
//		BatchTriggerConf conf = confDao.selectOne1(1000L);
//		System.out.println(conf.toString());
//		
//		conf.setTriggerRemark(conf.getTriggerRemark() + "----L被修改了");
//		confDao.updateOne1R(conf);
//		
//		System.out.println(">>>>>> " + confDao.selectOne1(1000L));
//		List<BatchTriggerConf> list = new ArrayList<>();
//		for(int i = 0; i < 5; i ++){
//			BatchTriggerConf triggerConf = new BatchTriggerConf();
//			triggerConf.setTriggerId(1001L + i);
//			triggerConf.setTriggerName("测试1-" + i);
//			triggerConf.setTriggerCrontrigger("*/5 * * * * ?");
//			triggerConf.setTriggerJobGroupId(5001L);
//			triggerConf.setTriggerMaintenanceDate("20191114");
//			triggerConf.setVaildStatus("0");
//			triggerConf.setTriggerRemark("每5秒执行一次(batchUpdateXML)--" + i);
//			list.add(triggerConf);
//		}
//		confDao.batchInsertXML(list);
//		confDao.batchUpdateXML1(list);
		List<BatchTriggerConf> list = confDao.selectAll1(5001L);
		System.out.println(">>>>>" + CommonUtils.toString(list));
	}
}
