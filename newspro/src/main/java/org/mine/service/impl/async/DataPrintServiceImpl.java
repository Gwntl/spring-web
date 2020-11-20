package org.mine.service.impl.async;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(value = "dataPrintServiceImpl")
public class DataPrintServiceImpl extends BaseServiceTaskletExecutor {
	@Override
	public Map<String, Object> executor(Map<String, Object> map) {
		logger.debug("DataPrintServiceImpl >>>> begin>>>>>{}", CommonUtils.toString(map));
		try{
			logger.debug("the current beanName : {}", beanName);
			logger.debug("second ActualAddJobImpl : {}", map.get("ActualAddJobImpl"));
			logger.debug("second operator : {}", map.get("operator"));
			TimeUnit.SECONDS.sleep(5);
			System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " dataPrintServiceImpl>>second job , second step>>> dataPrintServiceImpl>>>>>>");

//			Long count = GitContext.queryForLong("select count(1) from db_optimistic_lock");
//			if (count > 0L) {
//				throw GitWebException.GIT1001("条数大于一.");
//			}

		} catch (InterruptedException e){
			logger.error("error message : {}", MineException.getStackTrace(e));
//			Thread.currentThread().interrupt();
		}
		logger.debug("DataPrintServiceImpl >>>> end>>>>>");
		return null;
	}
}
