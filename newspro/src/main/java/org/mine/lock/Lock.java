package org.mine.lock;

import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: Lock
 * @date 2020/9/1719:55
 */
public interface Lock extends InitializingBean {
    /**
     * 等待时间
     */
    Long tryAgain = TimeUnit.SECONDS.toNanos(10);
    /**
     * 等待
     */
    default void waitMoment() {
        long againTime = System.nanoTime() + tryAgain;
        while (System.nanoTime() <= againTime) {
            //TODO 什么都不做.
        }
    }
}
