package org.mine.service.impl.async;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service(value = "actualAddJob")
public class ActualAddJobImpl extends BaseServiceTaskletExecutor {
	
	@Override
	public List<Map<String, Object>> doGrouping(Map<String, Object> input) {
		List<Map<String, Object>> maps = new ArrayList<>();
//		int count = 10000, groupNum = 2;
//		int no = 0;
//		Map<String, Object> map = null;
//		for(int i = 0; i < groupNum; i++){
//			map = new HashMap<>();
//			map.put("beginNo", no);
//			map.put("endNo", no = count/2);
//			map.put("jobID",input.getJobId());
//			maps.add(map);
//		}
		//mysql使用分页的形式进行分组操作limit beginNo, endNo;
		Long count = GitContext.queryForLong("SELECT COUNT(1) FROM BATCH_JOB_EXECUTE");
		int groupNum = 50;
		Map<String, Object> map = null;
		int beginNo = 0;
		while(count > beginNo){
			map = new HashMap<>();
			map.put("beginNo", beginNo);
			map.put("endNo", ((beginNo += groupNum) > count ? count : beginNo));
			maps.add(map);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("grouping size : {}", maps.size());
			logger.debug("grouping value : {}", CommonUtils.toString(maps));
		}
		return maps;
	}

	@Override
	public Map<String, Object> executor(Map<String, Object> map) {
		logger.debug("ActualAddJobImpl >>>> begin>>>>> {}", CommonUtils.toString(map));
		try{
			logger.debug("first operator : {}", map.get("operator"));
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e){
			logger.error("error message : {}", MineException.getStackTrace(e));
		}
		System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " actualAddJob>>>>>>>>first step>>> actualAddJob>>>>>>");
		logger.debug("ActualAddJobImpl >>>> end>>>>>");
//		throw new RuntimeException("执行失败.......");
		return null;
	}
}
