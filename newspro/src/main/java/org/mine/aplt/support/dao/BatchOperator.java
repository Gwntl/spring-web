package org.mine.aplt.support.dao;

import java.util.Map;

public interface BatchOperator {
	void call(Map<String, Object> map);
}
