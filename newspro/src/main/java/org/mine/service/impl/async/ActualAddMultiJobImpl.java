package org.mine.service.impl.async;

import java.util.Date;
import java.util.Map;

import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.util.CommonUtils;
import org.springframework.stereotype.Service;

/**
 * @Description: 
 * @ClassName: ActualAddMultiJobImpl
 * @author: wntl
 * @date: 2020年4月28日 下午7:30:32
 */
@Service(value = "actualAddMultiJob")
public class ActualAddMultiJobImpl extends BaseServiceTasketExcutor{

	/* (non-Javadoc)
	 * @see org.mine.aplt.support.ExcutorTask#call(java.util.Map)
	 */
	@Override
	public void call(Map<String, Object> map) {
		System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + ">>每40s跑一次>>>>>>>");
		
	}

}
