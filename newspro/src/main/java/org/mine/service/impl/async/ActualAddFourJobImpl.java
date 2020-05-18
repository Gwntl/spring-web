package org.mine.service.impl.async;

import java.util.Date;
import java.util.Map;

import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.util.CommonUtils;
import org.springframework.stereotype.Service;

@Service(value = "actualAddFourJob")
public class ActualAddFourJobImpl extends BaseServiceTasketExcutor {

	@Override
	public void call(Map<String, Object> map) {
		System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + ">>1s/>>>1004>>>>");
	}
}
