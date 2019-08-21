package org.mine.aplt.support.logger;

import org.slf4j.MDC;
import org.springframework.beans.factory.InitializingBean;

public class MDCLoggerInit implements InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
//		MDC.put("trade", "X");
		System.out.println("load>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public void init(){
		
	}

}