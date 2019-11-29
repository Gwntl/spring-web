package org.mine.model;

/**
 * batch_trigger_conf--定时任务触发器定义表
 * @filename BatchTriggerConf.java
 * @author wzaUsers
 * @date 2019-11-26 15:11:20
 * @version v1.0
*/
public class BatchTriggerConf {
	/**
	 * 触发器ID
	 */
	private Long triggerId;
	/**
	 * 触发器名
	 */
	private String triggerName;
	/**
	 * 触发器开始时间
	 */
	private String triggerStartTime;
	/**
	 * 触发器结束时间
	 */
	private String triggerEndTime;
	/**
	 * 触发器设置
	 */
	private String triggerCrontrigger;
	/**
	 * 维护日期
	 */
	private String triggerMaintenanceDate;
	/**
	 * 有效状态
	 */
	private String vaildStatus;
	/**
	 * 备注
	 */
	private String triggerRemark;

	public BatchTriggerConf() {
		this.triggerId = 0L;
		this.triggerName = "";
		this.triggerStartTime = null;
		this.triggerEndTime = null;
		this.triggerCrontrigger = "";
		this.triggerMaintenanceDate = "";
		this.vaildStatus = "0";
		this.triggerRemark = "";
	}

	/**
	 * 触发器ID
	 * @return thetriggerId
	 */
	public Long getTriggerId() {
		return triggerId;
	}
	/**
	 * 触发器ID
	 * @param triggerId the triggerId to set
	 */
	public void setTriggerId(Long triggerId) {
		this.triggerId = triggerId;
	}
	/**
	 * 触发器名
	 * @return thetriggerName
	 */
	public String getTriggerName() {
		return triggerName;
	}
	/**
	 * 触发器名
	 * @param triggerName the triggerName to set
	 */
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	/**
	 * 触发器开始时间
	 * @return thetriggerStartTime
	 */
	public String getTriggerStartTime() {
		return triggerStartTime;
	}
	/**
	 * 触发器开始时间
	 * @param triggerStartTime the triggerStartTime to set
	 */
	public void setTriggerStartTime(String triggerStartTime) {
		this.triggerStartTime = triggerStartTime;
	}
	/**
	 * 触发器结束时间
	 * @return thetriggerEndTime
	 */
	public String getTriggerEndTime() {
		return triggerEndTime;
	}
	/**
	 * 触发器结束时间
	 * @param triggerEndTime the triggerEndTime to set
	 */
	public void setTriggerEndTime(String triggerEndTime) {
		this.triggerEndTime = triggerEndTime;
	}
	/**
	 * 触发器设置
	 * @return thetriggerCrontrigger
	 */
	public String getTriggerCrontrigger() {
		return triggerCrontrigger;
	}
	/**
	 * 触发器设置
	 * @param triggerCrontrigger the triggerCrontrigger to set
	 */
	public void setTriggerCrontrigger(String triggerCrontrigger) {
		this.triggerCrontrigger = triggerCrontrigger;
	}
	/**
	 * 维护日期
	 * @return thetriggerMaintenanceDate
	 */
	public String getTriggerMaintenanceDate() {
		return triggerMaintenanceDate;
	}
	/**
	 * 维护日期
	 * @param triggerMaintenanceDate the triggerMaintenanceDate to set
	 */
	public void setTriggerMaintenanceDate(String triggerMaintenanceDate) {
		this.triggerMaintenanceDate = triggerMaintenanceDate;
	}
	/**
	 * 有效状态
	 * @return thevaildStatus
	 */
	public String getVaildStatus() {
		return vaildStatus;
	}
	/**
	 * 有效状态
	 * @param vaildStatus the vaildStatus to set
	 */
	public void setVaildStatus(String vaildStatus) {
		this.vaildStatus = vaildStatus;
	}
	/**
	 * 备注
	 * @return thetriggerRemark
	 */
	public String getTriggerRemark() {
		return triggerRemark;
	}
	/**
	 * 备注
	 * @param triggerRemark the triggerRemark to set
	 */
	public void setTriggerRemark(String triggerRemark) {
		this.triggerRemark = triggerRemark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchTriggerConf[" + 
		"triggerId=" + triggerId + ", triggerName=" + triggerName + ", triggerStartTime=" + triggerStartTime + ", triggerEndTime=" + triggerEndTime + 
		", triggerCrontrigger=" + triggerCrontrigger + ", triggerMaintenanceDate=" + triggerMaintenanceDate + ", vaildStatus=" + vaildStatus + 
		", triggerRemark=" + triggerRemark + "]";
	}
}