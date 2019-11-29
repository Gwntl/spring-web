package org.mine.aplt.support.dao;

import java.util.Map;

public interface BatchOperator {
	Object call(Map<String, Object> map);
}
