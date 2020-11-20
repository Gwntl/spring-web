package org.mine.lock.db;

import org.mine.aplt.exception.GitWebException;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.model.DbOptimisticLock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 队列执行数据库锁(乐观锁实现).
 * @author wntl
 * @version v1.0
 * @ClassName: QueueDBOptimisticLocking
 * @date 2020/9/16 14:09
 */
@Component
public class QueueDBOptimisticLocking extends DBLock {

    @Override
    public DBLockInfo tryLockRow(String lockName, String lockType) {
        //首先获取锁记录
        DbOptimisticLock optimisticLock = null;
        DBLockInfo lockInfo = null;
        try {
            optimisticLock = GitContext.queryForObject("select lock_name, lock_type, lock_status, lock_version, lock_owner, "
                            + "lock_time from db_optimistic_lock where lock_name = ? and lock_type = ?",
                    new Object[]{lockName, lockType}, DbOptimisticLock.class);
        } catch (EmptyResultDataAccessException e) {
            logger.error("data empty.");
            optimisticLock = null;
        } catch (Exception e) {
            logger.error("{}", GitWebException.getStackTrace(e));
        }

        String uuid = CommonUtils.getUUID();
        if (optimisticLock == null) {
            int version = 1;
            String ownerID = version + "_" + uuid;
            try {
                if (insertLockRow(lockName, lockType, version, ownerID)) {
                    logger.info("insert successful. {} get lock({}) successful.", Thread.currentThread().getName(), lockName);
                    lockInfo = new DBLockInfo();
                    lockInfo.setShareFlag(false);
                    lockInfo.setOwnerID(ownerID);
                    lockInfo.setVersion(version);
                    return lockInfo;
                } else {
                    logger.info("insert failed. {} get lock({}) failed.", Thread.currentThread().getName(), lockName);
                    return null;
                }
            } catch (DuplicateKeyException e) {
                logger.error("insert error. {} get lock({}) failed. ", Thread.currentThread().getName(), lockName);
                return null;
            } catch (Exception e) {
                logger.error("insert error : {}", GitWebException.getStackTrace(e));
                return null;
            }
        }

        //当锁被其他线程获取
        if (((lockInfo = lockMap.get(getKey(lockName, lockType))) != null && lockInfo.getStatus())
                || CommonUtils.equals(optimisticLock.getLockStatus(), "0")) {
            logger.info("{} get lock({}) failed. Has one thread in executing", Thread.currentThread().getName(), lockName);
            return null;
        }
        lockInfo = updateLockRow(optimisticLock, uuid);

        return lockInfo;
    }

    @Override
    public DBLockInfo lockRow(String lockName, String lockType) {
        DbOptimisticLock optimisticLock = null;
        DBLockInfo lockInfo = null;
        while (true) {
            try {
                optimisticLock = GitContext.queryForObject("select lock_name, lock_type, lock_status, lock_version, lock_owner, " +
                        "lock_time from db_optimistic_lock where lock_name = ? and lock_type = ?",
                        new Object[]{lockName, lockType}, DbOptimisticLock.class);
            } catch (Exception e) {
                logger.error("{}", GitWebException.getStackTrace(e));
            }

            int version = 1;
            String uuid = CommonUtils.getUUID();
            String ownerID = version + "_" + uuid;
            if (optimisticLock == null) {
                try {
                    if (insertLockRow(lockName, lockType, version, ownerID)) {
                        logger.info("insert successful. {} get lock({}) successful.", Thread.currentThread().getName(), lockName);
                        lockInfo = new DBLockInfo();
                        lockInfo.setShareFlag(false);
                        lockInfo.setOwnerID(ownerID);
                        lockInfo.setVersion(version);
                        return lockInfo;
                    } else {
                        logger.info("insert data into db failed. continue.");
                        continue;
                    }
                } catch (DuplicateKeyException e) {
                    logger.info("insert data into db error. continue.");
                    continue;
                } catch (Exception e) {
                    logger.error("insert error : {}", GitWebException.getStackTrace(e));
                    continue;
                }
            }

            if (((lockInfo = lockMap.get(getKey(lockName, lockType))) != null && lockInfo.getStatus())
                    || CommonUtils.equals(optimisticLock.getLockStatus(), "0")) {
                logger.info("the lock is held by another thread. continue.");
                waitMoment();
                continue;
            }

            lockInfo = updateLockRow(optimisticLock, uuid);

            if (lockInfo != null) {
                logger.info("update successful. {} get lock({}) successful.", Thread.currentThread().getName(), lockName);
                return lockInfo;
            } else {
                logger.info("update failed. the lock is held by another thread. continue.");
                continue;
            }
        }
    }

    @Override
    public boolean unLock(String lockName, String lockType, int lockVersion, String ownerID) {
        Object[] args = new Object[]{lockVersion + 1, lockName, lockType, lockVersion, ownerID};
        int r = GitContext.doIndependentTransActionControl(new BatchOperator<Integer, Object[]>() {
            @Override
            public Integer call(Object[] args) {
                String sql = "update db_optimistic_lock set lock_version = ?, lock_status = '1' where lock_name = ? " +
                        "and lock_type = ? and lock_version =? and lock_owner = ? and lock_status = '0'";
                return GitContext.update(sql, args);
            }
        }, args);

        return r > 0;
    }

    @Override
    boolean removeLock(String lockName, String lockType) {
        DbOptimisticLock optimisticLock = null;
        DBLockInfo lockInfo = null;
        try {
            optimisticLock = GitContext.queryForObject("select lock_name, lock_type, lock_status, lock_version, lock_owner, "
                            + "lock_time from db_optimistic_lock where lock_name = ? and lock_type = ?",
                    new Object[]{lockName, lockType}, DbOptimisticLock.class);
        } catch (Exception e) {
            logger.error("{}", GitWebException.getStackTrace(e));
        }
        if (optimisticLock == null) {
            logger.error("lock[{}] not exists.", getKey(lockType, lockType));
            return false;
        }

        int lockVersion = optimisticLock.getLockVersion();
        String ownerID = optimisticLock.getLockOwner();
        Object[] args = new Object[]{lockVersion + 1, lockName, lockType, lockVersion, ownerID};
        int r = GitContext.doIndependentTransActionControl(new BatchOperator<Integer, Object[]>() {
            @Override
            public Integer call(Object[] args) {
                String sql = "update db_optimistic_lock set lock_version = ?, lock_status = '2' where lock_name = ? " +
                        "and lock_type = ? and lock_version =? and lock_owner = ? and lock_status = '1'";
                return GitContext.update(sql, args);
            }
        }, args);

        if (r > 0) {
            lockMap.remove(getKey(lockName, lockType));
            return true;
        }

        return false;
    }

    /**
    * 更新锁信息
    * @param optimisticLock
    * @param uuid
    * @return: org.mine.lock.properties.DBLock.LockInfo
    * @Author: wntl
    * @Date: 2020/9/17
    */
    private DBLockInfo updateLockRow(DbOptimisticLock optimisticLock, String uuid){
        if (CommonUtils.isEmpty(optimisticLock)) {
            return null;
        }
        optimisticLock.setRemark(uuid);
        return GitContext.doIndependentTransActionControl(new BatchOperator<DBLockInfo, DbOptimisticLock>() {
            @Override
            public DBLockInfo call(DbOptimisticLock args) {
                String lockName = args.getLockName();
                String lockType = args.getLockType();
                int lockVersion = args.getLockVersion();
                int upVersion = lockVersion + 1;
                String ownerID = upVersion + "_" + args.getRemark();
                String sql = "update db_optimistic_lock set lock_version = ?, lock_owner = ?, update_time = ?, lock_status = '0' " +
                        "where lock_name = ? and lock_type = ? and lock_version =? and lock_owner = ? and lock_status = '1'";
                Object[] obj = new Object[]{upVersion, ownerID, CommonUtils.currentTime(new Date()), lockName, lockType,
                        lockVersion, args.getLockOwner()};
                DBLockInfo lockInfo = null;
                if (GitContext.update(sql, obj) > 0) {
                    lockInfo = new DBLockInfo();
                    lockInfo.setShareFlag(false);
                    lockInfo.setOwnerID(ownerID);
                    lockInfo.setVersion(upVersion);
                }
                return lockInfo;
            }
        }, optimisticLock);
    }
}
