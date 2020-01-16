package org.mine.quartz;

import java.util.List;
import java.util.Map;

public interface QuartzStepExecutor {

	public List<Object> grouping(Map<String, Object> basicMap);
	
	public void call(Object object);
	
}
