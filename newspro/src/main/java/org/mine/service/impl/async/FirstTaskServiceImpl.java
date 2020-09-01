package org.mine.service.impl.async;

import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: FirstTaskServiceImpl
 * @date 2020/8/2414:22
 */
@Service(value = "firstTask")
public class FirstTaskServiceImpl extends BaseServiceTaskletExecutor {
    @Override
    public Map<String, Object> executor(Map<String, Object> map) {
        logger.debug("FirstTaskServiceImpl begin>>>>>>>>>>>>>>>>>");
        try {
            logger.debug("休眠20秒.............");
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {

        }
        logger.debug("FirstTaskServiceImpl end<<<<<<<<<<<<<<<<<<<");
        return null;
    }
}
