package org.mine.model;

/**
 * batch_task_execution_log_register--批量任务执行日志登记表
 * @filename BatchTaskExecutionLogRegister.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/
public class BatchTaskExecutionLogRegister {
	/**
	 * 任务执行ID
	 */
	private String taskExecutionId;
	/**
	 * 执行作业ID
	 */
	private String taskJobId;
	/**
	 * 执行作业名称
	 */
	private String taskJobName;
	/**
	 * 任务ID
	 */
	private Long taskJobAssociateId;
	/**
	 * 开始时间
	 */
	private String taskStartTime;
	/**
	 * 结束时间
	 */
	private String taskEndTime;
	/**
	 * 任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 */
	private String taskJobStatus;
	/**
	 * 任务错误信息
	 */
	private String taskJobErrmsg;
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

	public BatchTaskExecutionLogRegister() {
		this.taskExecutionId = "";
		this.taskJobId = "";
		this.taskJobName = "";
		this.taskJobAssociateId = 0L;
		this.taskStartTime = null;
		this.taskEndTime = null;
		this.taskJobStatus = "";
		this.taskJobErrmsg = "";
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 任务执行ID
	 * @return the taskExecutionId
	 */
	public String getTaskExecutionId() {
		return taskExecutionId;
	}
	/**
	 * 任务执行ID
	 * @param taskExecutionId the taskExecutionId to set
	 */
	public void setTaskExecutionId(String taskExecutionId) {
		this.taskExecutionId = taskExecutionId;
	}
	/**
	 * 执行作业ID
	 * @return the taskJobId
	 */
	public String getTaskJobId() {
		return taskJobId;
	}
	/**
	 * 执行作业ID
	 * @param taskJobId the taskJobId to set
	 */
	public void setTaskJobId(String taskJobId) {
		this.taskJobId = taskJobId;
	}
	/**
	 * 执行作业名称
	 * @return the taskJobName
	 */
	public String getTaskJobName() {
		return taskJobName;
	}
	/**
	 * 执行作业名称
	 * @param taskJobName the taskJobName to set
	 */
	public void setTaskJobName(String taskJobName) {
		this.taskJobName = taskJobName;
	}
	/**
	 * 任务ID
	 * @return the taskJobAssociateId
	 */
	public Long getTaskJobAssociateId() {
		return taskJobAssociateId;
	}
	/**
	 * 任务ID
	 * @param taskJobAssociateId the taskJobAssociateId to set
	 */
	public void setTaskJobAssociateId(Long taskJobAssociateId) {
		this.taskJobAssociateId = taskJobAssociateId;
	}
	/**
	 * 开始时间
	 * @return the taskStartTime
	 */
	public String getTaskStartTime() {
		return taskStartTime;
	}
	/**
	 * 开始时间
	 * @param taskStartTime the taskStartTime to set
	 */
	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime = taskStartTime;
	}
	/**
	 * 结束时间
	 * @return the taskEndTime
	 */
	public String getTaskEndTime() {
		return taskEndTime;
	}
	/**
	 * 结束时间
	 * @param taskEndTime the taskEndTime to set
	 */
	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime = taskEndTime;
	}
	/**
	 * 任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @return the taskJobStatus
	 */
	public String getTaskJobStatus() {
		return taskJobStatus;
	}
	/**
	 * 任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param taskJobStatus the taskJobStatus to set
	 */
	public void setTaskJobStatus(String taskJobStatus) {
		this.taskJobStatus = taskJobStatus;
	}
	/**
	 * 任务错误信息
	 * @return the taskJobErrmsg
	 */
	public String getTaskJobErrmsg() {
		return taskJobErrmsg;
	}
	/**
	 * 任务错误信息
	 * @param taskJobErrmsg the taskJobErrmsg to set
	 */
	public void setTaskJobErrmsg(String taskJobErrmsg) {
		this.taskJobErrmsg = taskJobErrmsg;
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
		return "BatchTaskExecutionLogRegister[" + 
		"taskExecutionId=" + taskExecutionId + ", taskJobId=" + taskJobId + ", taskJobName=" + taskJobName + ", taskJobAssociateId=" + taskJobAssociateId + 
		", taskStartTime=" + taskStartTime + ", taskEndTime=" + taskEndTime + ", taskJobStatus=" + taskJobStatus + 
		", taskJobErrmsg=" + taskJobErrmsg + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}