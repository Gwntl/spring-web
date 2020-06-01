package org.mine.service.impl.async;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.util.CommonUtils;
import org.mine.quartz.dto.GroupInputDto;
import org.springframework.stereotype.Service;

@Service(value = "actualAddJob")
public class ActualAddJobImpl extends BaseServiceTasketExcutor{
	
	@Override
	public List<Map<String, Object>> doGrouping(GroupInputDto input) {
		List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
		int count = 10000, groupNum = 2;
		int no = 0;
		Map<String, Object> map = null;
		for(int i = 0; i < groupNum; i++){
			map = new HashMap<>();
			map.put("beginNo", no);
			map.put("endNo", no = count/2);
			maps.add(map);
		}
		return maps;
	}

	@Override
	public Map<String, Object> excutor(Map<String, Object> map) {
		logger.debug("ActualAddJobImpl >>>> begin>>>>>");
		try{
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e){
			logger.error("error message : {}", MineException.getStackTrace(e));
		}
		System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " >second step>>> actualAddJob>>>>>>");
		logger.debug("ActualAddJobImpl >>>> end>>>>>");
		return null;
	}
}
