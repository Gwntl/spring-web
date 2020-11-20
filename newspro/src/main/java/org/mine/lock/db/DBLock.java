package org.mine.lock.db;

import org.mine.aplt.constant.JobConstant;
import org.mine.aplt.support.bean.GitContext;
import org.mine.aplt.support.dao.BatchOperator;
import org.mine.aplt.util.CommonUtils;
import org.mine.lock.Lock;
import org.mine.lock.LockWorker;
import org.mine.model.DbOptimisticLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 数据库锁
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: DBLock
 * @date 2020/9/1616:51
 */
public abstract class DBLock implements Lock {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static Map<String, DBLockInfo> lockMap = new ConcurrentHashMap<>(64);

    protected static Properties properties;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static long timeOut = 60 * 60 * 1000;

    private static long warningOut = 30 * 60 * 1000;

    protected static long tryAgain = TimeUnit.SECONDS.toNanos(5);

    /**
    * 尝试获取锁. 立即返回结果.
    * @param lockName 资源名称
    * @param lockType 类别
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/9/16
    */
    abstract DBLockInfo tryLockRow(String lockName, String lockType);

    /**
    * 获取锁. 自旋.
    * @param lockName 资源名称
    * @param lockType 类别
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/9/16
    */
    abstract DBLockInfo lockRow(String lockName, String lockType);

    /**
     * 释放锁
     * @param lockName 资源名称
     * @param lockType 类别
     * @param lockVersion 版本号
     * @param ownerID
     * @return: boolean
     * @Author: wntl
     * @Date: 2020/9/16
     */
    abstract boolean unLock(String lockName, String lockType, int lockVersion, String ownerID);

    /**
     * 移除锁信息
     * @param lockName
     * @param lockType
     * @return
     */
    abstract boolean removeLock(String lockName, String lockType);

    /**
    * 尝试获取锁
    * @param lockName
    * @param lockType
    * @param lockWorker
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/21
    */
    public void tryLock(String lockName, String lockType, LockWorker lockWorker) {
        String key = getKey(lockName, lockType);
        DBLockInfo lockInfo = null;
        if ((lockInfo = lockMap.get(key)) != null && lockInfo.getStatus()) {
            logger.info(">>>>>>>the lock is held by another thread. exit.{}", Thread.currentThread().getName());
            return;
        }

        lockInfo = tryLockRow(lockName, lockType);
        if (lockInfo != null) {
            logger.info("{} get lock({}) successful.", Thread.currentThread().getName(), lockName);
            lockInfo.setStatus(true);
            lockMap.put(key, lockInfo);
            lockWorker.worker(lockInfo);
            unLock(lockName, lockType, lockInfo.getVersion(), lockInfo.getOwnerID());
            lockMap.get(key).setStatus(false);
        } else {
            logger.info("{} update failed. the lock is held by another thread. exit.", Thread.currentThread().getName());
        }
    }

    /**
    * 阻塞获取锁
    * @param lockName
    * @param lockType
    * @param lockWorker
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/21
    */
    public void lock(String lockName, String lockType, LockWorker lockWorker) {
        //阻塞获取锁
        DBLockInfo lockInfo = lockRow(lockName, lockType);
        if (lockInfo != null) {
            String key = getKey(lockName, lockType);
            lockInfo.setStatus(true);
            lockMap.put(key, lockInfo);
            lockWorker.worker(lockInfo);
            unLock(lockName, lockType, lockInfo.getVersion(), lockInfo.getOwnerID());
            lockMap.get(key).setStatus(false);
        }
    }

    /**
     * 获取key值
     * @param lockName
     * @param lockType
     * @return
     */
    public String getKey(String lockName, String lockType) { return lockName + "-" + lockType; }

    /**
     * 等待
     */
    public void waitMoment() {
        long againTime = System.nanoTime() + tryAgain;
        while (System.nanoTime() <= againTime) {
            //TODO 什么都不做.
        }
    }

    /**
     * 插入独占锁记录
     * @param lockName 锁名称
     * @param lockType 锁类型
     * @return: boolean
     * @Author: wntl
     * @Date: 2020/9/16
     */
    protected boolean insertLockRow(String lockName, String lockType, int version, String ownerID) {
        Long lockTime = CommonUtils.toLong(properties.getProperty(getKey(lockName, lockType)));
        return insertLockRow(lockName, lockType, (lockTime == null || lockTime > 0L) ? 0L : lockTime, version, ownerID, false);
    }

    /**
     * 插入锁记录
     * @param lockName 锁名称
     * @param lockType 锁类型
     * @param lockTime 锁有效时间
     * @return: boolean
     * @Author: wntl
     * @Date: 2020/9/16
     */
    protected boolean insertLockRow(String lockName, String lockType, Long lockTime, int version, String ownerID, boolean isShared) {
        String currentTime = CommonUtils.currentTime(new Date());
        Object [] args = new Object[]{lockName, lockType, "0", ownerID, version, lockTime, currentTime,
                ((lockTime != null && lockTime > 0L) ? (currentTime + lockTime * 1000) : null), currentTime, JobConstant.VALID_STATUS_0};
        int result = GitContext.doIndependentTransActionControl(new BatchOperator<Integer, Object []>() {
            @Override
            public Integer call(Object [] args) {
                String sql = "insert into db_optimistic_lock (lock_name, lock_type, lock_status, lock_owner, lock_version, "
                        + "lock_time, create_time, end_time, update_time, valid_status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                return GitContext.update(sql, args);
            }
        }, args);
        return result > 0;
    }

    static {
        try {
            properties = new Properties();
            properties.load(DBLock.class.getClassLoader().getResourceAsStream("config/properties/lock/lock.properties"));
        } catch (IOException e) {
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                List<DbOptimisticLock> list = GitContext.queryForList(
                        "select lock_name, lock_type, lock_version, lock_owner, lock_time, end_time, update_time "
                        + "from db_optimistic_lock where lock_status = '0'", null, DbOptimisticLock.class);
                logger.debug("scan data : {}", CommonUtils.toString(list));
                if (list != null && list.size() > 0) {
                    Iterator<DbOptimisticLock> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        DbOptimisticLock optimisticLock = iterator.next();
                        try {
                            Long current = System.currentTimeMillis();
                            Long lockTime = optimisticLock.getLockTime() * 1000;
                            Long end = lockTime > 0L ? formatter.parse(optimisticLock.getEndTime()).getTime() : 0L;
                            Long update  = formatter.parse(optimisticLock.getUpdateTime()).getTime();
                            String key = getKey(optimisticLock.getLockName(), optimisticLock.getLockType());
                            DBLockInfo lockInfo = lockMap.get(key);
                            if (lockTime > 0 && current > end && (CommonUtils.equals(lockInfo.getVersion(), optimisticLock.getLockVersion())
                                    && CommonUtils.equals(lockInfo.getOwnerID(), optimisticLock.getLockOwner()) && !lockInfo.getStatus())) {
                                //更新数据状态
                                int r = GitContext.doIndependentTransActionControl(new BatchOperator<Integer, DbOptimisticLock>() {
                                    @Override
                                    public Integer call(DbOptimisticLock input) {
                                        return GitContext.update("update db_optimistic_lock set update_time = ?, lock_status = '1' where lock_name = ? " +
                                                        "and lock_type = ? and lock_version = ? and lock_owner = ? and lock_status = '0'",
                                                new Object[]{CommonUtils.currentTime(new Date()), input.getLockName(), input.getLockType(),
                                                        input.getLockVersion(), input.getLockOwner()});
                                    }
                                }, optimisticLock);
                                if (r > 0) {
                                    logger.info("remove lock successful.");
                                }
                            } else if (lockTime == 0L) {
                                if (((current - update) / timeOut) > 0 && (CommonUtils.equals(lockInfo.getVersion(), optimisticLock.getLockVersion())
                                        && CommonUtils.equals(lockInfo.getOwnerID(), optimisticLock.getLockOwner()) && !lockInfo.getStatus())) {
                                    GitContext.doIndependentTransActionControl(new BatchOperator<Integer, DbOptimisticLock>() {
                                        @Override
                                        public Integer call(DbOptimisticLock input) {
                                            return GitContext.update("update db_optimistic_lock set update_time = ?, lock_status = '1' where lock_name = ? " +
                                                      "and lock_type = ? and lock_version = ? and lock_owner = ? and lock_status = '0'",
                                                new Object[]{CommonUtils.currentTime(new Date()), input.getLockName(), input.getLockType(),
                                                        input.getLockVersion(), input.getLockOwner()});
                                        }
                                    }, optimisticLock);
                                } else if (((current - update) / warningOut) > 0 && (CommonUtils.equals(lockInfo.getVersion(), optimisticLock.getLockVersion())
                                        && CommonUtils.equals(lockInfo.getOwnerID(), optimisticLock.getLockOwner()) && lockInfo.getStatus())) {
                                    //监控报警
                                    logger.error("====== {} holds the lock for more than a specified time.", optimisticLock.getLockName());
                                }
                            }
                        } catch (ParseException e) {

                        }
                    }
                }
            }
        }, 0, 10000);
    }
}
