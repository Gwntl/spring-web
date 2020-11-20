package org.mine.model;

/**
 * db_optimistic_lock--数据库乐观式分布式锁
 * @filename DbOptimisticLock.java
 * @author wzaUsers
 * @date 2020-09-18 16:09:00
 * @version v1.0
*/
public class DbOptimisticLock {
	/**
	 * 名字
	 */
	private String lockName;
	/**
	 * 类别
	 */
	private String lockType;
	/**
	 * 状态
	 */
	private String lockStatus;
	/**
	 * 版本号
	 */
	private Integer lockVersion;
	/**
	 * 拥有者
	 */
	private String lockOwner;
	/**
	 * 有效时间. 以秒为单位
	 */
	private Long lockTime;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 到期时间
	 */
	private String endTime;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 有效状态. 0-是, 1-否
	 */
	private String validStatus;
	/**
	 * 备注
	 */
	private String remark;

	public DbOptimisticLock() {
		this.lockName = "";
		this.lockType = "";
		this.lockStatus = "";
		this.lockVersion = 0;
		this.lockOwner = "";
		this.lockTime = 0L;
		this.createTime = null;
		this.endTime = null;
		this.updateTime = null;
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 名字
	 * @return the lockName
	 */
	public String getLockName() {
		return lockName;
	}
	/**
	 * 名字
	 * @param lockName the lockName to set
	 */
	public void setLockName(String lockName) {
		this.lockName = lockName;
	}
	/**
	 * 类别
	 * @return the lockType
	 */
	public String getLockType() {
		return lockType;
	}
	/**
	 * 类别
	 * @param lockType the lockType to set
	 */
	public void setLockType(String lockType) {
		this.lockType = lockType;
	}
	/**
	 * 状态
	 * @return the lockStatus
	 */
	public String getLockStatus() {
		return lockStatus;
	}
	/**
	 * 状态
	 * @param lockStatus the lockStatus to set
	 */
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
	/**
	 * 版本号
	 * @return the lockVersion
	 */
	public Integer getLockVersion() {
		return lockVersion;
	}
	/**
	 * 版本号
	 * @param lockVersion the lockVersion to set
	 */
	public void setLockVersion(Integer lockVersion) {
		this.lockVersion = lockVersion;
	}
	/**
	 * 拥有者
	 * @return the lockOwner
	 */
	public String getLockOwner() {
		return lockOwner;
	}
	/**
	 * 拥有者
	 * @param lockOwner the lockOwner to set
	 */
	public void setLockOwner(String lockOwner) {
		this.lockOwner = lockOwner;
	}
	/**
	 * 有效时间. 以秒为单位
	 * @return the lockTime
	 */
	public Long getLockTime() {
		return lockTime;
	}
	/**
	 * 有效时间. 以秒为单位
	 * @param lockTime the lockTime to set
	 */
	public void setLockTime(Long lockTime) {
		this.lockTime = lockTime;
	}
	/**
	 * 创建时间
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * 创建时间
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 到期时间
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 到期时间
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 更新时间
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * 更新时间
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 有效状态. 0-是, 1-否
	 * @return the validStatus
	 */
	public String getValidStatus() {
		return validStatus;
	}
	/**
	 * 有效状态. 0-是, 1-否
	 * @param validStatus the validStatus to set
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	/**
	 * 备注
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 备注
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DbOptimisticLock[" + 
		"lockName=" + lockName + ", lockType=" + lockType + ", lockStatus=" + lockStatus + ", lockVersion=" + lockVersion + 
		", lockOwner=" + lockOwner + ", lockTime=" + lockTime + ", createTime=" + createTime + 
		", endTime=" + endTime + ", updateTime=" + updateTime + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}