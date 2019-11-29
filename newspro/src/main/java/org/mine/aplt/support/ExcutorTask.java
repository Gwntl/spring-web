package org.mine.aplt.support;

import java.util.Map;

public interface ExcutorTask {

	Map<String, Object> excutor(Map<String, Object> map);
	
	void call(Map<String, Object> map);
}
