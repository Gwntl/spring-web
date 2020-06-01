package org.mine.service.impl.async;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.util.CommonUtils;
import org.springframework.stereotype.Service;

@Service(value = "dataPrintServiceImpl")
public class DataPrintServiceImpl extends BaseServiceTasketExcutor{
	
	@Override
	public Map<String, Object> excutor(Map<String, Object> map) {
		logger.debug("DataPrintServiceImpl >>>> begin>>>>>");
		try{
			TimeUnit.SECONDS.sleep(14);
			System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " >>second job , three step>>> dataPrintServiceImpl>>>>>>");
		} catch (InterruptedException e){
			logger.error("error message : {}", MineException.getStackTrace(e));
		}
		logger.debug("DataPrintServiceImpl >>>> end>>>>>");
		return null;
	}
}
