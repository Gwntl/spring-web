package org.mine.aplt.support;

import java.util.Map;

public interface ExcutorTask {
	/**
	 * 执行实体
	 * @param map
	 * @return
	 */
	Map<String, Object> excutor(Map<String, Object> map);
	/**
	 * 异步逻辑代码执行模板
	 * @param map
	 * @return
	 */
	Map<String, Object> handler(Map<String, Object> map);
}
