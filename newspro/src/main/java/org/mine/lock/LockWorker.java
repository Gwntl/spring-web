package org.mine.lock;

import org.mine.lock.db.DBLockInfo;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: LockWorker
 * @date 2020/9/1719:29
 */
public interface LockWorker {
    void worker(DBLockInfo lockInfo);
}
