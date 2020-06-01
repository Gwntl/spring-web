package org.mine.service.impl.async;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.util.CommonUtils;
import org.mine.dao.BatchQueueDefinitionDao;
import org.mine.dao.BatchTriggerDefinitionDao;
import org.mine.model.BatchQueueDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 
 * @ClassName: ActualAddMultiJobImpl
 * @author: wntl
 * @date: 2020年4月28日 下午7:30:32
 */
@Service(value = "actualAddMultiJob")
public class ActualAddMultiJobImpl extends BaseServiceTasketExcutor{

	@Autowired
	private BatchTriggerDefinitionDao triggerDao;
	
	@Autowired
	private BatchQueueDefinitionDao queueDao;
	
	/* (non-Javadoc)
	 * @see org.mine.aplt.support.ExcutorTask#call(java.util.Map)
	 */
	@Override
	public Map<String, Object> excutor(Map<String, Object> map) {
		logger.debug("ActualAddMultiJobImpl >>>> begin>>>>>");
		Map<String, Object> output = null;
		try{
			TimeUnit.SECONDS.sleep(10);
			System.out.println(triggerDao.selectOne1R(2L, true).toString());
			System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " >>second job , first step>>> actualAddMultiJob>>>>>>");
			
			BatchQueueDefinition queueDefinition = new BatchQueueDefinition();
			queueDefinition.setQueueId(5L);
			queueDefinition.setQueueName("INSERT_TEST_5L");
			queueDefinition.setQueueExecutionNum(1);
			queueDefinition.setQueueTimingtaskFlag(1);
			queueDefinition.setRemark("R");
			queueDefinition.setValidStatus("R");
			queueDefinition.setCreateDate("20200529");
			queueDao.insertOne(queueDefinition);
			output = new HashMap<String, Object>();
			output.put("actualAddMultiJob", "actualAddMultiJob_success");
		} catch (InterruptedException e){
			logger.error("error message : {}", MineException.getStackTrace(e));
			//手动置中断状态, sleep会清除中断状态.
//			Thread.currentThread().interrupt();
//			logger.warn("我被中断了, 此时我把事务回滚, 做一些通知信息等.");
		}
		logger.debug("ActualAddMultiJobImpl >>>> end>>>>>");
		return output;
	}
}
