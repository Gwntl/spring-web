package org.mine.model;

/**
 * batch_step_log--批量定时任务日志登记表
 * @filename BatchStepLog.java
 * @author wzaUsers
 * @date 2020-09-24 14:09:29
 * @version v1.0
*/
public class BatchStepLog {
	/**
	 * 步骤执行实例
	 */
	private String executionId;
	/**
	 * JOB执行实例
	 */
	private String executionInstance;
	/**
	 * 执行步骤ID
	 */
	private String stepId;
	/**
	 * 执行步骤名称
	 */
	private String stepName;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 */
	private String stepStatus;
	/**
	 * 步骤错误信息
	 */
	private String stepErrmsg;
	/**
	 * 关联作业ID
	 */
	private String associateJobId;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 时间戳
	 */
	private Long timeStamp;
	/**
	 * 有效状态. 0-是, 1-否
	 */
	private String validStatus;
	/**
	 * 备注
	 */
	private String remark;

	public BatchStepLog() {
		this.executionId = "";
		this.executionInstance = "";
		this.stepId = "";
		this.stepName = "";
		this.startTime = null;
		this.endTime = null;
		this.stepStatus = "";
		this.stepErrmsg = "";
		this.associateJobId = "";
		this.createDate = "";
		this.timeStamp = 0L;
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 步骤执行实例
	 * @return the executionId
	 */
	public String getExecutionId() {
		return executionId;
	}
	/**
	 * 步骤执行实例
	 * @param executionId the executionId to set
	 */
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	/**
	 * JOB执行实例
	 * @return the executionInstance
	 */
	public String getExecutionInstance() {
		return executionInstance;
	}
	/**
	 * JOB执行实例
	 * @param executionInstance the executionInstance to set
	 */
	public void setExecutionInstance(String executionInstance) {
		this.executionInstance = executionInstance;
	}
	/**
	 * 执行步骤ID
	 * @return the stepId
	 */
	public String getStepId() {
		return stepId;
	}
	/**
	 * 执行步骤ID
	 * @param stepId the stepId to set
	 */
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	/**
	 * 执行步骤名称
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}
	/**
	 * 执行步骤名称
	 * @param stepName the stepName to set
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	/**
	 * 开始时间
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 开始时间
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 结束时间
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 结束时间
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @return the stepStatus
	 */
	public String getStepStatus() {
		return stepStatus;
	}
	/**
	 * 步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param stepStatus the stepStatus to set
	 */
	public void setStepStatus(String stepStatus) {
		this.stepStatus = stepStatus;
	}
	/**
	 * 步骤错误信息
	 * @return the stepErrmsg
	 */
	public String getStepErrmsg() {
		return stepErrmsg;
	}
	/**
	 * 步骤错误信息
	 * @param stepErrmsg the stepErrmsg to set
	 */
	public void setStepErrmsg(String stepErrmsg) {
		this.stepErrmsg = stepErrmsg;
	}
	/**
	 * 关联作业ID
	 * @return the associateJobId
	 */
	public String getAssociateJobId() {
		return associateJobId;
	}
	/**
	 * 关联作业ID
	 * @param associateJobId the associateJobId to set
	 */
	public void setAssociateJobId(String associateJobId) {
		this.associateJobId = associateJobId;
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
	 * 时间戳
	 * @return the timeStamp
	 */
	public Long getTimeStamp() {
		return timeStamp;
	}
	/**
	 * 时间戳
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
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
		return "BatchStepLog[" + 
		"executionId=" + executionId + ", executionInstance=" + executionInstance + ", stepId=" + stepId + ", stepName=" + stepName + 
		", startTime=" + startTime + ", endTime=" + endTime + ", stepStatus=" + stepStatus + 
		", stepErrmsg=" + stepErrmsg + ", associateJobId=" + associateJobId + ", createDate=" + createDate + 
		", timeStamp=" + timeStamp + ", validStatus=" + validStatus + ", remark=" + remark + 
		"]";
	}
}