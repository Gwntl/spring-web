package org.mine.service.impl.async;

import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.mine.aplt.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: SecondTaskServiceImpl
 * @date 2020/8/2414:22
 */
@Service(value = "secondTask")
public class SecondTaskServiceImpl extends BaseServiceTaskletExecutor{
    @Override
    public List<Map<String, Object>> doGrouping(Map<String, Object> input) {
        List<Map<String, Object>> maps = new ArrayList<>();
        int end = 40;
        int start = 30;
        Map<String, Object> objectMap = null;
        for (int i = start; i < end; i++) {
            objectMap = new HashMap<>();
            objectMap.put("sleepTime", i + "");
            maps.add(objectMap);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("grouping size : {}", maps.size());
            logger.debug("grouping value : {}", CommonUtils.toString(maps));
        }
        return maps;
    }

    @Override
    public Map<String, Object> executor(Map<String, Object> map) {
        logger.debug("SecondTaskServiceImpl begin>>>>>>>>>>>>>>>>>");
        logger.debug("input ; {}", CommonUtils.toString(map));
        try {
            logger.debug("休眠{}}秒.............", map.get("sleepTime"));
            TimeUnit.SECONDS.sleep(Long.valueOf((map.get("sleepTime") + "").trim()));
        } catch (InterruptedException e) {

        }
        logger.debug("SecondTaskServiceImpl end<<<<<<<<<<<<<<<<<<<");
        return null;
    }
}
