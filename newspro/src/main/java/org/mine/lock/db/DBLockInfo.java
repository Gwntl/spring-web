package org.mine.lock.db;

/**
 * 锁信息
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: LockInfo
 * @date 2020/9/1719:32
 */
public class DBLockInfo {
    /**
     * 状态
     */
    private boolean status;
    /**
     * 拥有者ID
     */
    private String ownerID;
    /**
     * 版本号
     */
    private int version;
    /**
     * 是否为共享锁
     */
    private boolean shareFlag;
    /**
     * 锁数量
     */
    private int shareNum;

    /**
     * 状态
     * @return the status as status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * 拥有者ID
     * @return the ownerID as ownerID
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * 拥有者ID
     * @param ownerID the ownerID to set
     */
    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * 版本号
     * @return the version as version
     */
    public int getVersion() {
        return version;
    }

    /**
     * 版本号
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * 是否为共享锁
     * @return the shareFlag as shareFlag
     */
    public boolean getShareFlag() {
        return shareFlag;
    }

    /**
     * 是否为共享锁
     * @param shareFlag the shareFlag to set
     */
    public void setShareFlag(boolean shareFlag) {
        this.shareFlag = shareFlag;
    }

    /**
     * 锁数量
     * @return the shareNum as shareNum
     */
    public int getShareNum() {
        return shareNum;
    }

    /**
     * 锁数量
     * @param shareNum the shareNum to set
     */
    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    @Override
    public String toString() {
        return "LockInfo{status=" + status + ", ownerID='" + ownerID + '\'' + ", version=" + version + ", shareFlag="
                + shareFlag + ", shareNum=" + shareNum + '}';
    }
}
