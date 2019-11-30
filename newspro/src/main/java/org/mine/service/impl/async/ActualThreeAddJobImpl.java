package org.mine.service.impl.async;

import java.util.Date;
import java.util.Map;

import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.util.CommonUtils;

public class ActualThreeAddJobImpl extends BaseServiceTasketExcutor{

	@Override
	public void call(Map<String, Object> map) {
		System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + ActualThreeAddJobImpl.class.getSimpleName() + ">>>>>success>>>>>");
	}

}
