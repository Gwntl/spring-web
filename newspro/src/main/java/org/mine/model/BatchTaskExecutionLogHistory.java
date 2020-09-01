package org.mine.model;

/**
 * batch_task_execution_log_history--批量任务执行日志历史表
 * @filename BatchTaskExecutionLogHistory.java
 * @author wzaUsers
 * @date 2020-08-20 11:08:58
 * @version v1.0
*/
public class BatchTaskExecutionLogHistory {
	/**
	 * 任务执行ID
	 */
	private String historyTaskExecutionId;
	/**
	 * 执行作业ID
	 */
	private String historyTaskJobId;
	/**
	 * 执行作业名称
	 */
	private String historyTaskJobName;
	/**
	 * 开始时间
	 */
	private String historyTaskStartTime;
	/**
	 * 结束时间
	 */
	private String historyTaskEndTime;
	/**
	 * 任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 */
	private String historyTaskJobStatus;
	/**
	 * 任务错误信息
	 */
	private String historyTaskJobErrmsg;
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

	public BatchTaskExecutionLogHistory() {
		this.historyTaskExecutionId = "";
		this.historyTaskJobId = "";
		this.historyTaskJobName = "";
		this.historyTaskStartTime = null;
		this.historyTaskEndTime = null;
		this.historyTaskJobStatus = "";
		this.historyTaskJobErrmsg = "";
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 任务执行ID
	 * @return the historyTaskExecutionId
	 */
	public String getHistoryTaskExecutionId() {
		return historyTaskExecutionId;
	}
	/**
	 * 任务执行ID
	 * @param historyTaskExecutionId the historyTaskExecutionId to set
	 */
	public void setHistoryTaskExecutionId(String historyTaskExecutionId) {
		this.historyTaskExecutionId = historyTaskExecutionId;
	}
	/**
	 * 执行作业ID
	 * @return the historyTaskJobId
	 */
	public String getHistoryTaskJobId() {
		return historyTaskJobId;
	}
	/**
	 * 执行作业ID
	 * @param historyTaskJobId the historyTaskJobId to set
	 */
	public void setHistoryTaskJobId(String historyTaskJobId) {
		this.historyTaskJobId = historyTaskJobId;
	}
	/**
	 * 执行作业名称
	 * @return the historyTaskJobName
	 */
	public String getHistoryTaskJobName() {
		return historyTaskJobName;
	}
	/**
	 * 执行作业名称
	 * @param historyTaskJobName the historyTaskJobName to set
	 */
	public void setHistoryTaskJobName(String historyTaskJobName) {
		this.historyTaskJobName = historyTaskJobName;
	}
	/**
	 * 开始时间
	 * @return the historyTaskStartTime
	 */
	public String getHistoryTaskStartTime() {
		return historyTaskStartTime;
	}
	/**
	 * 开始时间
	 * @param historyTaskStartTime the historyTaskStartTime to set
	 */
	public void setHistoryTaskStartTime(String historyTaskStartTime) {
		this.historyTaskStartTime = historyTaskStartTime;
	}
	/**
	 * 结束时间
	 * @return the historyTaskEndTime
	 */
	public String getHistoryTaskEndTime() {
		return historyTaskEndTime;
	}
	/**
	 * 结束时间
	 * @param historyTaskEndTime the historyTaskEndTime to set
	 */
	public void setHistoryTaskEndTime(String historyTaskEndTime) {
		this.historyTaskEndTime = historyTaskEndTime;
	}
	/**
	 * 任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @return the historyTaskJobStatus
	 */
	public String getHistoryTaskJobStatus() {
		return historyTaskJobStatus;
	}
	/**
	 * 任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param historyTaskJobStatus the historyTaskJobStatus to set
	 */
	public void setHistoryTaskJobStatus(String historyTaskJobStatus) {
		this.historyTaskJobStatus = historyTaskJobStatus;
	}
	/**
	 * 任务错误信息
	 * @return the historyTaskJobErrmsg
	 */
	public String getHistoryTaskJobErrmsg() {
		return historyTaskJobErrmsg;
	}
	/**
	 * 任务错误信息
	 * @param historyTaskJobErrmsg the historyTaskJobErrmsg to set
	 */
	public void setHistoryTaskJobErrmsg(String historyTaskJobErrmsg) {
		this.historyTaskJobErrmsg = historyTaskJobErrmsg;
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
		return "BatchTaskExecutionLogHistory[" + 
		"historyTaskExecutionId=" + historyTaskExecutionId + ", historyTaskJobId=" + historyTaskJobId + ", historyTaskJobName=" + historyTaskJobName + ", historyTaskStartTime=" + historyTaskStartTime + 
		", historyTaskEndTime=" + historyTaskEndTime + ", historyTaskJobStatus=" + historyTaskJobStatus + ", historyTaskJobErrmsg=" + historyTaskJobErrmsg + 
		", createDate=" + createDate + ", validStatus=" + validStatus + ", remark=" + remark + 
		"]";
	}
}