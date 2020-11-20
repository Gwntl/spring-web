package org.mine.lock.db;

/**
 * 数据库共享锁未实现.
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: QueueDBShareOptimisticLocking
 * @date 2020/9/1814:26
 */
public class QueueDBShareOptimisticLocking extends DBLock {
    @Override
    DBLockInfo tryLockRow(String lockName, String lockType) {
        return trySharedLockRow(lockName, lockType);
    }

    @Override
    DBLockInfo lockRow(String lockName, String lockType) {
        return sharedLockRow(lockName, lockType);
    }

    @Override
    boolean unLock(String lockName, String lockType, int lockVersion, String ownerID) {
        return false;
    }

    @Override
    boolean removeLock(String lockName, String lockType) {
        return false;
    }

    DBLockInfo trySharedLockRow(String lockName, String lockType) {


        return null;
    }

    DBLockInfo sharedLockRow(String lockName, String lockType) {
        return null;
    }
}
