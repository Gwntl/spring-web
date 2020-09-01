package org.mine.service.impl.async;

import org.mine.aplt.support.BaseServiceTaskletExecutor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: FiveTaskServiceImpl
 * @date 2020/8/259:46
 */
@Service(value = "fiveTask")
public class FiveTaskServiceImpl extends BaseServiceTaskletExecutor{
    @Override
    public Map<String, Object> executor(Map<String, Object> map) {
        logger.debug("FiveTaskServiceImpl begin>>>>>>>>>>>>>>>>>");
        try {
            logger.debug("休眠35秒.............");
            TimeUnit.SECONDS.sleep(35);
//            throw GitWebException.GIT1001(this.getClass().getSimpleName() + "获取资源时失败.");
        } catch (InterruptedException e) {

        }
        logger.debug("FiveTaskServiceImpl end<<<<<<<<<<<<<<<<<<<");
        return null;
    }
}
