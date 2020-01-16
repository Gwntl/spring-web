package org.mine.model;

/**
 * batch_timing_task_log_register--批量定时任务日志登记表
 * @filename BatchTimingTaskLogRegister.java
 * @author wzaUsers
 * @date 2020-01-09 15:01:44
 * @version v1.0
*/
public class BatchTimingTaskLogRegister {
	/**
	 * 定时任务执行ID
	 */
	private Long timingExecutionId;
	/**
	 * 定时作业ID
	 */
	private Long timingJobId;
	/**
	 * 定时作业名称
	 */
	private String timingJobName;
	/**
	 * 任务ID
	 */
	private Long timingAssociateTaskId;
	/**
	 * 开始时间
	 */
	private String timingStartTime;
	/**
	 * 结束时间
	 */
	private String timingEndTime;
	/**
	 * 定时任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 */
	private String timingJobStatus;
	/**
	 * 定时任务错误信息
	 */
	private String timingJobErrmsg;
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

	public BatchTimingTaskLogRegister() {
		this.timingExecutionId = 0L;
		this.timingJobId = 0L;
		this.timingJobName = "";
		this.timingAssociateTaskId = 0L;
		this.timingStartTime = null;
		this.timingEndTime = null;
		this.timingJobStatus = "";
		this.timingJobErrmsg = "";
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 定时任务执行ID
	 * @return thetimingExecutionId
	 */
	public Long getTimingExecutionId() {
		return timingExecutionId;
	}
	/**
	 * 定时任务执行ID
	 * @param timingExecutionId the timingExecutionId to set
	 */
	public void setTimingExecutionId(Long timingExecutionId) {
		this.timingExecutionId = timingExecutionId;
	}
	/**
	 * 定时作业ID
	 * @return thetimingJobId
	 */
	public Long getTimingJobId() {
		return timingJobId;
	}
	/**
	 * 定时作业ID
	 * @param timingJobId the timingJobId to set
	 */
	public void setTimingJobId(Long timingJobId) {
		this.timingJobId = timingJobId;
	}
	/**
	 * 定时作业名称
	 * @return thetimingJobName
	 */
	public String getTimingJobName() {
		return timingJobName;
	}
	/**
	 * 定时作业名称
	 * @param timingJobName the timingJobName to set
	 */
	public void setTimingJobName(String timingJobName) {
		this.timingJobName = timingJobName;
	}
	/**
	 * 任务ID
	 * @return thetimingAssociateTaskId
	 */
	public Long getTimingAssociateTaskId() {
		return timingAssociateTaskId;
	}
	/**
	 * 任务ID
	 * @param timingAssociateTaskId the timingAssociateTaskId to set
	 */
	public void setTimingAssociateTaskId(Long timingAssociateTaskId) {
		this.timingAssociateTaskId = timingAssociateTaskId;
	}
	/**
	 * 开始时间
	 * @return thetimingStartTime
	 */
	public String getTimingStartTime() {
		return timingStartTime;
	}
	/**
	 * 开始时间
	 * @param timingStartTime the timingStartTime to set
	 */
	public void setTimingStartTime(String timingStartTime) {
		this.timingStartTime = timingStartTime;
	}
	/**
	 * 结束时间
	 * @return thetimingEndTime
	 */
	public String getTimingEndTime() {
		return timingEndTime;
	}
	/**
	 * 结束时间
	 * @param timingEndTime the timingEndTime to set
	 */
	public void setTimingEndTime(String timingEndTime) {
		this.timingEndTime = timingEndTime;
	}
	/**
	 * 定时任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @return thetimingJobStatus
	 */
	public String getTimingJobStatus() {
		return timingJobStatus;
	}
	/**
	 * 定时任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param timingJobStatus the timingJobStatus to set
	 */
	public void setTimingJobStatus(String timingJobStatus) {
		this.timingJobStatus = timingJobStatus;
	}
	/**
	 * 定时任务错误信息
	 * @return thetimingJobErrmsg
	 */
	public String getTimingJobErrmsg() {
		return timingJobErrmsg;
	}
	/**
	 * 定时任务错误信息
	 * @param timingJobErrmsg the timingJobErrmsg to set
	 */
	public void setTimingJobErrmsg(String timingJobErrmsg) {
		this.timingJobErrmsg = timingJobErrmsg;
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
	 * 有效状态. 0-是, 1-否
	 * @return thevalidStatus
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
		return "BatchTimingTaskLogRegister[" + 
		"timingExecutionId=" + timingExecutionId + ", timingJobId=" + timingJobId + ", timingJobName=" + timingJobName + ", timingAssociateTaskId=" + timingAssociateTaskId + 
		", timingStartTime=" + timingStartTime + ", timingEndTime=" + timingEndTime + ", timingJobStatus=" + timingJobStatus + 
		", timingJobErrmsg=" + timingJobErrmsg + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}