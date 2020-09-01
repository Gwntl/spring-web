package org.mine.model;

/**
 * batch_task_definition--批量任务定义表
 * @filename BatchTaskDefinition.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/
public class BatchTaskDefinition {
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 是否自动执行. 0-是, 1-否
	 */
	private Integer taskAutoFlag;
	/**
	 * 关联定时器ID
	 */
	private String taskAssociateTriggerId;
	/**
	 * 任务执行器
	 */
	private String taskExecutor;
	/**
	 * 可跳过标志. 0-是, 1-否
	 */
	private Integer taskSkipFlag;
	/**
	 * 任务初始值
	 */
	private String taskInitValue;
	/**
	 * 任务并发数
	 */
	private Integer taskConcurrencyNum;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 有效状态. 0-是, 1-否, D-已废弃
	 */
	private String validStatus;
	/**
	 * 备注
	 */
	private String remark;

	public BatchTaskDefinition() {
		this.taskId = "";
		this.taskName = "";
		this.taskAutoFlag = 1;
		this.taskAssociateTriggerId = "";
		this.taskExecutor = "";
		this.taskSkipFlag = 1;
		this.taskInitValue = "";
		this.taskConcurrencyNum = 0;
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 任务ID
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}
	/**
	 * 任务ID
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/**
	 * 任务名称
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * 任务名称
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * 是否自动执行. 0-是, 1-否
	 * @return the taskAutoFlag
	 */
	public Integer getTaskAutoFlag() {
		return taskAutoFlag;
	}
	/**
	 * 是否自动执行. 0-是, 1-否
	 * @param taskAutoFlag the taskAutoFlag to set
	 */
	public void setTaskAutoFlag(Integer taskAutoFlag) {
		this.taskAutoFlag = taskAutoFlag;
	}
	/**
	 * 关联定时器ID
	 * @return the taskAssociateTriggerId
	 */
	public String getTaskAssociateTriggerId() {
		return taskAssociateTriggerId;
	}
	/**
	 * 关联定时器ID
	 * @param taskAssociateTriggerId the taskAssociateTriggerId to set
	 */
	public void setTaskAssociateTriggerId(String taskAssociateTriggerId) {
		this.taskAssociateTriggerId = taskAssociateTriggerId;
	}
	/**
	 * 任务执行器
	 * @return the taskExecutor
	 */
	public String getTaskExecutor() {
		return taskExecutor;
	}
	/**
	 * 任务执行器
	 * @param taskExecutor the taskExecutor to set
	 */
	public void setTaskExecutor(String taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	/**
	 * 可跳过标志. 0-是, 1-否
	 * @return the taskSkipFlag
	 */
	public Integer getTaskSkipFlag() {
		return taskSkipFlag;
	}
	/**
	 * 可跳过标志. 0-是, 1-否
	 * @param taskSkipFlag the taskSkipFlag to set
	 */
	public void setTaskSkipFlag(Integer taskSkipFlag) {
		this.taskSkipFlag = taskSkipFlag;
	}
	/**
	 * 任务初始值
	 * @return the taskInitValue
	 */
	public String getTaskInitValue() {
		return taskInitValue;
	}
	/**
	 * 任务初始值
	 * @param taskInitValue the taskInitValue to set
	 */
	public void setTaskInitValue(String taskInitValue) {
		this.taskInitValue = taskInitValue;
	}
	/**
	 * 任务并发数
	 * @return the taskConcurrencyNum
	 */
	public Integer getTaskConcurrencyNum() {
		return taskConcurrencyNum;
	}
	/**
	 * 任务并发数
	 * @param taskConcurrencyNum the taskConcurrencyNum to set
	 */
	public void setTaskConcurrencyNum(Integer taskConcurrencyNum) {
		this.taskConcurrencyNum = taskConcurrencyNum;
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
	 * 有效状态. 0-是, 1-否, D-已废弃
	 * @return the validStatus
	 */
	public String getValidStatus() {
		return validStatus;
	}
	/**
	 * 有效状态. 0-是, 1-否, D-已废弃
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
		return "BatchTaskDefinition[" + 
		"taskId=" + taskId + ", taskName=" + taskName + ", taskAutoFlag=" + taskAutoFlag + ", taskAssociateTriggerId=" + taskAssociateTriggerId + 
		", taskExecutor=" + taskExecutor + ", taskSkipFlag=" + taskSkipFlag + ", taskInitValue=" + taskInitValue + 
		", taskConcurrencyNum=" + taskConcurrencyNum + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}