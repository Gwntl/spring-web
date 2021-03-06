package org.mine.service.impl.async;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchQueueDefinitionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(value = "actualThreeAddJob")
public class ActualThreeAddJobImpl extends BaseServiceTaskletExecutor {

	@Autowired
	private BatchQueueDefinitionDao queueDao;
	
	@Override
	public Map<String, Object> executor(Map<String, Object> map) {
		logger.debug("ActualThreeAddJobImpl >>>> begin>>>>>");
		try{
			logger.debug("three operator : {}", map.get("operator"));
			System.out.println("!!!!!!!: "+ map.get("actualAddMultiJob"));
			logger.debug("insert begin.....");
//			BatchQueueDefinition queueDefinition = new BatchQueueDefinition();
//			queueDefinition.setQueueId(6 + "");
//			queueDefinition.setQueueName("INSERT_TEST_6L");
//			queueDefinition.setQueueExecutionNum(1);
//			queueDefinition.setRemark("R");
//			queueDefinition.setValidStatus("R");
//			queueDefinition.setCreateDate("20200529");
//			queueDao.insertOne(queueDefinition);
			logger.debug("insert end.....");
			
			TimeUnit.SECONDS.sleep(5);
			System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " actualThreeAddJob>>>> three step>>> actualThreeAddJob>>>>>>");
//			if(map.containsKey("failed")){
//				throw GitWebException.GIT1001("手动置错!!!!!!");
//			}
		} catch (InterruptedException e){
			logger.error("error message : {}", MineException.getStackTrace(e));
			//手动置中断状态, sleep会清除中断状态.
//			Thread.currentThread().interrupt();
			logger.warn("我被中断了, 此时我把事务回滚, 做一些通知信息等.");
		}
		logger.debug("ActualThreeAddJobImpl >>>> end>>>>>");
		return null;
	}
}
