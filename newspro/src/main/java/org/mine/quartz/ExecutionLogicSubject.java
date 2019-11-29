package org.mine.quartz;

import java.util.Map;

public interface ExecutionLogicSubject {
	/**
	 * 执行逻辑主体
	 */
	void logicalSubject(Map<String, Object> groupInfo);
}
