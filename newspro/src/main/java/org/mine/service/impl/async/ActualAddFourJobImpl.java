package org.mine.service.impl.async;

import org.mine.aplt.exception.MineException;
import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(value = "actualAddFourJob")
public class ActualAddFourJobImpl extends BaseServiceTaskletExecutor {

	@Override
	public Map<String, Object> executor(Map<String, Object> map) {
		logger.debug("ActualAddFourJobImpl >>>> begin>>>>>");
		try{
			logger.debug("coming...... sleping.....");
			TimeUnit.SECONDS.sleep(10);
			logger.debug("sleep over......");
		} catch (InterruptedException e){
			logger.error("error message : {}", MineException.getStackTrace(e));
		}
		System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + ">>first step>>>actualAddFourJob>>>>");
		logger.debug("ActualAddFourJobImpl >>>> end>>>>>");
		return null;
	}
}
