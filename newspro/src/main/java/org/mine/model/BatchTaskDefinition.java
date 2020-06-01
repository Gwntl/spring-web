package org.mine.model;

/**
 * batch_task_definition--批量任务定义表
 * @filename BatchTaskDefinition.java
 * @author wzaUsers
 * @date 2020-06-01 15:06:24
 * @version v1.0
*/
public class BatchTaskDefinition {
	/**
	 * 任务ID
	 */
	private Long taskId;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 关联任务组ID
	 */
	private Long taskAssociateGroupId;
	/**
	 * 可跳过标志. 0-是, 1-否
	 */
	private Integer taskSkipFlag;
	/**
	 * 任务初始值
	 */
	private String taskInitValue;
	/**
	 * 任务执行序号
	 */
	private Integer taskExecutionNum;
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
		this.taskId = 0L;
		this.taskName = "";
		this.taskAssociateGroupId = 0L;
		this.taskSkipFlag = 1;
		this.taskInitValue = "";
		this.taskExecutionNum = 0;
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 任务ID
	 * @return thetaskId
	 */
	public Long getTaskId() {
		return taskId;
	}
	/**
	 * 任务ID
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	/**
	 * 任务名称
	 * @return thetaskName
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
	 * 关联任务组ID
	 * @return thetaskAssociateGroupId
	 */
	public Long getTaskAssociateGroupId() {
		return taskAssociateGroupId;
	}
	/**
	 * 关联任务组ID
	 * @param taskAssociateGroupId the taskAssociateGroupId to set
	 */
	public void setTaskAssociateGroupId(Long taskAssociateGroupId) {
		this.taskAssociateGroupId = taskAssociateGroupId;
	}
	/**
	 * 可跳过标志. 0-是, 1-否
	 * @return thetaskSkipFlag
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
	 * @return thetaskInitValue
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
	 * 任务执行序号
	 * @return thetaskExecutionNum
	 */
	public Integer getTaskExecutionNum() {
		return taskExecutionNum;
	}
	/**
	 * 任务执行序号
	 * @param taskExecutionNum the taskExecutionNum to set
	 */
	public void setTaskExecutionNum(Integer taskExecutionNum) {
		this.taskExecutionNum = taskExecutionNum;
	}
	/**
	 * 创建时间
	 * @return thecreateDate
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
	 * @return thevalidStatus
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
	 * @return theremark
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
		"taskId=" + taskId + ", taskName=" + taskName + ", taskAssociateGroupId=" + taskAssociateGroupId + ", taskSkipFlag=" + taskSkipFlag + 
		", taskInitValue=" + taskInitValue + ", taskExecutionNum=" + taskExecutionNum + ", createDate=" + createDate + 
		", validStatus=" + validStatus + ", remark=" + remark + "]";
	}
}