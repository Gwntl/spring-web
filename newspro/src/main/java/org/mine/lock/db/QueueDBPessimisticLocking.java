package org.mine.lock.db;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 队列执行数据库锁(悲观锁实现). for update. 需要关闭mysql的自动提交
 * 队列执行时获取数据库锁, 如果获取锁失败,表明当前存在队列执行, 则当前队列退出,不需要继续获取锁.
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: QueueDBLock
 * @date 2020/9/1415:29
 */
@Component
public class QueueDBPessimisticLocking {
    private static Map<String, Long> lockMap = new ConcurrentHashMap<>(64);

    /**
    * 获取锁. 快速失败.
    * @param lockName
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/9/14
    */
    public boolean lock(final String lockName) {return false;}

    /**
    * 释放锁
    * @param lockValue
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/14
    */
    public boolean unLock(String lockValue) {return false;}

    /**
    * 插入独占锁记录
    * @param lockValue
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/9/14
    */
    private boolean insertLockRow(String lockValue) {
        return false;
    }

    private boolean insertLockRow(String lockValue, String lockName, Long lockTime){return false;}
}
