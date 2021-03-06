package org.mine.model;

/**
 * batch_step_execution_log_register--批量步骤执行日志登记表
 * @filename BatchStepExecutionLogRegister.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/
public class BatchStepExecutionLogRegister {
	/**
	 * 任务执行ID
	 */
	private String stepExecutionId;
	/**
	 * 执行步骤ID
	 */
	private String stepJobId;
	/**
	 * 执行步骤名称
	 */
	private String stepJobName;
	/**
	 * 开始时间
	 */
	private String stepStartTime;
	/**
	 * 结束时间
	 */
	private String stepEndTime;
	/**
	 * 步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 */
	private String stepJobStatus;
	/**
	 * 步骤错误信息
	 */
	private String stepJobErrmsg;
	/**
	 * 关联作业ID
	 */
	private Long stepAssociateJobId;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 有效状态. 0-是, 1-否
	 */
	private String validStatus;
	/**
	 * 备注
	 */
	private String remark;

	public BatchStepExecutionLogRegister() {
		this.stepExecutionId = "";
		this.stepJobId = "";
		this.stepJobName = "";
		this.stepStartTime = null;
		this.stepEndTime = null;
		this.stepJobStatus = "";
		this.stepJobErrmsg = "";
		this.stepAssociateJobId = 0L;
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 任务执行ID
	 * @return the stepExecutionId
	 */
	public String getStepExecutionId() {
		return stepExecutionId;
	}
	/**
	 * 任务执行ID
	 * @param stepExecutionId the stepExecutionId to set
	 */
	public void setStepExecutionId(String stepExecutionId) {
		this.stepExecutionId = stepExecutionId;
	}
	/**
	 * 执行步骤ID
	 * @return the stepJobId
	 */
	public String getStepJobId() {
		return stepJobId;
	}
	/**
	 * 执行步骤ID
	 * @param stepJobId the stepJobId to set
	 */
	public void setStepJobId(String stepJobId) {
		this.stepJobId = stepJobId;
	}
	/**
	 * 执行步骤名称
	 * @return the stepJobName
	 */
	public String getStepJobName() {
		return stepJobName;
	}
	/**
	 * 执行步骤名称
	 * @param stepJobName the stepJobName to set
	 */
	public void setStepJobName(String stepJobName) {
		this.stepJobName = stepJobName;
	}
	/**
	 * 开始时间
	 * @return the stepStartTime
	 */
	public String getStepStartTime() {
		return stepStartTime;
	}
	/**
	 * 开始时间
	 * @param stepStartTime the stepStartTime to set
	 */
	public void setStepStartTime(String stepStartTime) {
		this.stepStartTime = stepStartTime;
	}
	/**
	 * 结束时间
	 * @return the stepEndTime
	 */
	public String getStepEndTime() {
		return stepEndTime;
	}
	/**
	 * 结束时间
	 * @param stepEndTime the stepEndTime to set
	 */
	public void setStepEndTime(String stepEndTime) {
		this.stepEndTime = stepEndTime;
	}
	/**
	 * 步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @return the stepJobStatus
	 */
	public String getStepJobStatus() {
		return stepJobStatus;
	}
	/**
	 * 步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param stepJobStatus the stepJobStatus to set
	 */
	public void setStepJobStatus(String stepJobStatus) {
		this.stepJobStatus = stepJobStatus;
	}
	/**
	 * 步骤错误信息
	 * @return the stepJobErrmsg
	 */
	public String getStepJobErrmsg() {
		return stepJobErrmsg;
	}
	/**
	 * 步骤错误信息
	 * @param stepJobErrmsg the stepJobErrmsg to set
	 */
	public void setStepJobErrmsg(String stepJobErrmsg) {
		this.stepJobErrmsg = stepJobErrmsg;
	}
	/**
	 * 关联作业ID
	 * @return the stepAssociateJobId
	 */
	public Long getStepAssociateJobId() {
		return stepAssociateJobId;
	}
	/**
	 * 关联作业ID
	 * @param stepAssociateJobId the stepAssociateJobId to set
	 */
	public void setStepAssociateJobId(Long stepAssociateJobId) {
		this.stepAssociateJobId = stepAssociateJobId;
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
		return "BatchStepExecutionLogRegister[" + 
		"stepExecutionId=" + stepExecutionId + ", stepJobId=" + stepJobId + ", stepJobName=" + stepJobName + ", stepStartTime=" + stepStartTime + 
		", stepEndTime=" + stepEndTime + ", stepJobStatus=" + stepJobStatus + ", stepJobErrmsg=" + stepJobErrmsg + 
		", stepAssociateJobId=" + stepAssociateJobId + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}