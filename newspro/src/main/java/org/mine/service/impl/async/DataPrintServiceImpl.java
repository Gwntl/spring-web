package org.mine.service.impl.async;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mine.aplt.support.BaseServiceTasketExcutor;
import org.mine.aplt.util.CommonUtils;
import org.mine.service.DataPrintService;
import org.springframework.stereotype.Repository;

@Repository
public class DataPrintServiceImpl extends BaseServiceTasketExcutor implements DataPrintService{

	@Override
	public List<Map<String, Object>> grouping(Map<String, Object> map) {
		return super.grouping(map);
	}
	
	@Override
	public void call(Map<String, Object> map) {
		System.out.println(CommonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " : " + "-->>>>-----");
	}
}
