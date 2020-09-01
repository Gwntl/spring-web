package org.mine.service.impl.async;

import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: FourTaskServiceImpl
 * @date 2020/8/2414:23
 */
@Service(value = "fourTask")
public class FourTaskServiceImpl extends BaseServiceTaskletExecutor{

    @Override
    public Map<String, Object> executor(Map<String, Object> map) {
        logger.debug("FourTaskServiceImpl begin>>>>>>>>>>>>>>>>>");
        try {
            logger.debug("休眠18秒.............");
            TimeUnit.SECONDS.sleep(18);
        } catch (InterruptedException e) {

        }
        logger.debug("FourTaskServiceImpl end<<<<<<<<<<<<<<<<<<<");
        return null;
    }
}
