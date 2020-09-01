package org.mine.service.impl.async;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.util.CommonUtils;
import org.springframework.stereotype.Service;

@Service(value = "dataPrintServiceImpl")
public class DataPrintServiceImpl extends BaseServiceTaskletExecutor {
	
	@Override
	public Map<String, Object> executor(Map<String, Object> map) {
		logger.debug("DataPrintServiceImpl >>>> begin>>>>>");
		try{
			logger.debug("second operator : {}", map.get("operator"));
			TimeUnit.SECONDS.sleep(3);
			System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " dataPrintServiceImpl>>second job , second step>>> dataPrintServiceImpl>>>>>>");
		} catch (InterruptedException e){
			logger.error("error message : {}", MineException.getStackTrace(e));
		}
		logger.debug("DataPrintServiceImpl >>>> end>>>>>");
		return null;
	}
}
