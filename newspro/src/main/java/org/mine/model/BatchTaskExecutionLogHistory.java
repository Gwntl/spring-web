package org.mine.model;

/**
 * batch_task_execution_log_history--
 * @filename BatchTaskExecutionLogHistory.java
 * @author wzaUsers
 * @date 2020-04-30 16:04:06
 * @version v1.0
*/
public class BatchTaskExecutionLogHistory {
	/**
	 * 任务执行ID
	 */
	private Long historyTaskExecutionId;
	/**
	 * 执行作业ID
	 */
	private Long historyTaskJobId;
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
		this.historyTaskExecutionId = 0L;
		this.historyTaskJobId = 0L;
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
	 * @return thehistoryTaskExecutionId
	 */
	public Long getHistoryTaskExecutionId() {
		return historyTaskExecutionId;
	}
	/**
	 * 任务执行ID
	 * @param historyTaskExecutionId the historyTaskExecutionId to set
	 */
	public void setHistoryTaskExecutionId(Long historyTaskExecutionId) {
		this.historyTaskExecutionId = historyTaskExecutionId;
	}
	/**
	 * 执行作业ID
	 * @return thehistoryTaskJobId
	 */
	public Long getHistoryTaskJobId() {
		return historyTaskJobId;
	}
	/**
	 * 执行作业ID
	 * @param historyTaskJobId the historyTaskJobId to set
	 */
	public void setHistoryTaskJobId(Long historyTaskJobId) {
		this.historyTaskJobId = historyTaskJobId;
	}
	/**
	 * 执行作业名称
	 * @return thehistoryTaskJobName
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
	 * @return thehistoryTaskStartTime
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
	 * @return thehistoryTaskEndTime
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
	 * @return thehistoryTaskJobStatus
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
	 * @return thehistoryTaskJobErrmsg
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
		return "BatchTaskExecutionLogHistory[" + 
		"historyTaskExecutionId=" + historyTaskExecutionId + ", historyTaskJobId=" + historyTaskJobId + ", historyTaskJobName=" + historyTaskJobName + ", historyTaskStartTime=" + historyTaskStartTime + 
		", historyTaskEndTime=" + historyTaskEndTime + ", historyTaskJobStatus=" + historyTaskJobStatus + ", historyTaskJobErrmsg=" + historyTaskJobErrmsg + 
		", createDate=" + createDate + ", validStatus=" + validStatus + ", remark=" + remark + 
		"]";
	}
}