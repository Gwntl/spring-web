package org.mine.service.impl.async;

import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: ThreeTaskServiceImpl
 * @date 2020/8/2414:23
 */
@Service(value = "threeTask")
public class ThreeTaskServiceImpl extends BaseServiceTaskletExecutor{
    @Override
    public Map<String, Object> executor(Map<String, Object> map) {
        logger.debug("ThreeTaskServiceImpl begin>>>>>>>>>>>>>>>>>");
        try {
            logger.debug("休眠10秒.............");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {

        }
        logger.debug("ThreeTaskServiceImpl end<<<<<<<<<<<<<<<<<<<");
        return null;
    }
}
