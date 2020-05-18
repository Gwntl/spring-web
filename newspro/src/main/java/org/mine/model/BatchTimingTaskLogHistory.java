package org.mine.model;

/**
 * batch_timing_task_log_history--
 * @filename BatchTimingTaskLogHistory.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:06
 * @version v1.0
*/
public class BatchTimingTaskLogHistory {
	/**
	 * 定时任务执行ID
	 */
	private Long historyTimingExecutionId;
	/**
	 * 定时作业ID
	 */
	private Long historyTimingJobId;
	/**
	 * 定时作业名称
	 */
	private String historyTimingJobName;
	/**
	 * 任务ID
	 */
	private Long historyTimingAssociateTaskId;
	/**
	 * 开始时间
	 */
	private String historyTimingStartTime;
	/**
	 * 结束时间
	 */
	private String historyTimingEndTime;
	/**
	 * 定时任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 */
	private String historyTimingJobStatus;
	/**
	 * 定时任务错误信息
	 */
	private String historyTimingJobErrmsg;
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

	public BatchTimingTaskLogHistory() {
		this.historyTimingExecutionId = 0L;
		this.historyTimingJobId = 0L;
		this.historyTimingJobName = "";
		this.historyTimingAssociateTaskId = 0L;
		this.historyTimingStartTime = null;
		this.historyTimingEndTime = null;
		this.historyTimingJobStatus = "";
		this.historyTimingJobErrmsg = "";
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 定时任务执行ID
	 * @return thehistoryTimingExecutionId
	 */
	public Long getHistoryTimingExecutionId() {
		return historyTimingExecutionId;
	}
	/**
	 * 定时任务执行ID
	 * @param historyTimingExecutionId the historyTimingExecutionId to set
	 */
	public void setHistoryTimingExecutionId(Long historyTimingExecutionId) {
		this.historyTimingExecutionId = historyTimingExecutionId;
	}
	/**
	 * 定时作业ID
	 * @return thehistoryTimingJobId
	 */
	public Long getHistoryTimingJobId() {
		return historyTimingJobId;
	}
	/**
	 * 定时作业ID
	 * @param historyTimingJobId the historyTimingJobId to set
	 */
	public void setHistoryTimingJobId(Long historyTimingJobId) {
		this.historyTimingJobId = historyTimingJobId;
	}
	/**
	 * 定时作业名称
	 * @return thehistoryTimingJobName
	 */
	public String getHistoryTimingJobName() {
		return historyTimingJobName;
	}
	/**
	 * 定时作业名称
	 * @param historyTimingJobName the historyTimingJobName to set
	 */
	public void setHistoryTimingJobName(String historyTimingJobName) {
		this.historyTimingJobName = historyTimingJobName;
	}
	/**
	 * 任务ID
	 * @return thehistoryTimingAssociateTaskId
	 */
	public Long getHistoryTimingAssociateTaskId() {
		return historyTimingAssociateTaskId;
	}
	/**
	 * 任务ID
	 * @param historyTimingAssociateTaskId the historyTimingAssociateTaskId to set
	 */
	public void setHistoryTimingAssociateTaskId(Long historyTimingAssociateTaskId) {
		this.historyTimingAssociateTaskId = historyTimingAssociateTaskId;
	}
	/**
	 * 开始时间
	 * @return thehistoryTimingStartTime
	 */
	public String getHistoryTimingStartTime() {
		return historyTimingStartTime;
	}
	/**
	 * 开始时间
	 * @param historyTimingStartTime the historyTimingStartTime to set
	 */
	public void setHistoryTimingStartTime(String historyTimingStartTime) {
		this.historyTimingStartTime = historyTimingStartTime;
	}
	/**
	 * 结束时间
	 * @return thehistoryTimingEndTime
	 */
	public String getHistoryTimingEndTime() {
		return historyTimingEndTime;
	}
	/**
	 * 结束时间
	 * @param historyTimingEndTime the historyTimingEndTime to set
	 */
	public void setHistoryTimingEndTime(String historyTimingEndTime) {
		this.historyTimingEndTime = historyTimingEndTime;
	}
	/**
	 * 定时任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @return thehistoryTimingJobStatus
	 */
	public String getHistoryTimingJobStatus() {
		return historyTimingJobStatus;
	}
	/**
	 * 定时任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param historyTimingJobStatus the historyTimingJobStatus to set
	 */
	public void setHistoryTimingJobStatus(String historyTimingJobStatus) {
		this.historyTimingJobStatus = historyTimingJobStatus;
	}
	/**
	 * 定时任务错误信息
	 * @return thehistoryTimingJobErrmsg
	 */
	public String getHistoryTimingJobErrmsg() {
		return historyTimingJobErrmsg;
	}
	/**
	 * 定时任务错误信息
	 * @param historyTimingJobErrmsg the historyTimingJobErrmsg to set
	 */
	public void setHistoryTimingJobErrmsg(String historyTimingJobErrmsg) {
		this.historyTimingJobErrmsg = historyTimingJobErrmsg;
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
		return "BatchTimingTaskLogHistory[" + 
		"historyTimingExecutionId=" + historyTimingExecutionId + ", historyTimingJobId=" + historyTimingJobId + ", historyTimingJobName=" + historyTimingJobName + ", historyTimingAssociateTaskId=" + historyTimingAssociateTaskId + 
		", historyTimingStartTime=" + historyTimingStartTime + ", historyTimingEndTime=" + historyTimingEndTime + ", historyTimingJobStatus=" + historyTimingJobStatus + 
		", historyTimingJobErrmsg=" + historyTimingJobErrmsg + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}