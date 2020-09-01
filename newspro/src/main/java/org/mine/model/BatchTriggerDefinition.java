package org.mine.model;

/**
 * batch_trigger_definition--定时任务触发器定义表
 * @filename BatchTriggerDefinition.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/
public class BatchTriggerDefinition {
	/**
	 * 触发器ID
	 */
	private String triggerId;
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
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 有效状态
	 */
	private String validStatus;
	/**
	 * 备注
	 */
	private String triggerRemark;

	public BatchTriggerDefinition() {
		this.triggerId = "";
		this.triggerName = "";
		this.triggerStartTime = null;
		this.triggerEndTime = null;
		this.triggerCrontrigger = "";
		this.createDate = "";
		this.validStatus = "0";
		this.triggerRemark = "";
	}

	/**
	 * 触发器ID
	 * @return the triggerId
	 */
	public String getTriggerId() {
		return triggerId;
	}
	/**
	 * 触发器ID
	 * @param triggerId the triggerId to set
	 */
	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}
	/**
	 * 触发器名
	 * @return the triggerName
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
	 * @return the triggerStartTime
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
	 * @return the triggerEndTime
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
	 * @return the triggerCrontrigger
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
	 * 创建时间
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * 创建时间
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * 有效状态
	 * @return the validStatus
	 */
	public String getValidStatus() {
		return validStatus;
	}
	/**
	 * 有效状态
	 * @param validStatus the validStatus to set
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}
	/**
	 * 备注
	 * @return the triggerRemark
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
		return "BatchTriggerDefinition[" + 
		"triggerId=" + triggerId + ", triggerName=" + triggerName + ", triggerStartTime=" + triggerStartTime + ", triggerEndTime=" + triggerEndTime + 
		", triggerCrontrigger=" + triggerCrontrigger + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", triggerRemark=" + triggerRemark + "]";
	}
}