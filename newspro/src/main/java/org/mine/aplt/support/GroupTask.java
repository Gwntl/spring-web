package org.mine.aplt.support;

import java.util.List;
import java.util.Map;

import org.mine.quartz.dto.GroupInputDto;

public interface GroupTask{
	List<Map<String, Object>> doGrouping(GroupInputDto input);
}
