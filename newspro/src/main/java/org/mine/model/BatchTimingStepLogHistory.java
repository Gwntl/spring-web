package org.mine.model;

/**
 * batch_timing_step_log_history--定时批量步骤执行日志历史表
 * @filename BatchTimingStepLogHistory.java
 * @author wzaUsers
 * @date 2020-09-14 19:09:48
 * @version v1.0
*/
public class BatchTimingStepLogHistory {
	/**
	 * 任务执行ID
	 */
	private Long historyStepExecutionId;
	/**
	 * 执行步骤ID
	 */
	private Long historyStepJobId;
	/**
	 * 执行步骤名称
	 */
	private String historyStepJobName;
	/**
	 * 开始时间
	 */
	private String historyStepStartTime;
	/**
	 * 结束时间
	 */
	private String historyStepEndTime;
	/**
	 * 步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 */
	private String historyStepJobStatus;
	/**
	 * 步骤错误信息
	 */
	private String historyStepJobErrmsg;
	/**
	 * 关联作业ID
	 */
	private Long historyStepAssociateJobId;
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

	public BatchTimingStepLogHistory() {
		this.historyStepExecutionId = 0L;
		this.historyStepJobId = 0L;
		this.historyStepJobName = "";
		this.historyStepStartTime = null;
		this.historyStepEndTime = null;
		this.historyStepJobStatus = "";
		this.historyStepJobErrmsg = "";
		this.historyStepAssociateJobId = 0L;
		this.createDate = "";
		this.validStatus = "0";
		this.remark = "";
	}

	/**
	 * 任务执行ID
	 * @return the historyStepExecutionId
	 */
	public Long getHistoryStepExecutionId() {
		return historyStepExecutionId;
	}
	/**
	 * 任务执行ID
	 * @param historyStepExecutionId the historyStepExecutionId to set
	 */
	public void setHistoryStepExecutionId(Long historyStepExecutionId) {
		this.historyStepExecutionId = historyStepExecutionId;
	}
	/**
	 * 执行步骤ID
	 * @return the historyStepJobId
	 */
	public Long getHistoryStepJobId() {
		return historyStepJobId;
	}
	/**
	 * 执行步骤ID
	 * @param historyStepJobId the historyStepJobId to set
	 */
	public void setHistoryStepJobId(Long historyStepJobId) {
		this.historyStepJobId = historyStepJobId;
	}
	/**
	 * 执行步骤名称
	 * @return the historyStepJobName
	 */
	public String getHistoryStepJobName() {
		return historyStepJobName;
	}
	/**
	 * 执行步骤名称
	 * @param historyStepJobName the historyStepJobName to set
	 */
	public void setHistoryStepJobName(String historyStepJobName) {
		this.historyStepJobName = historyStepJobName;
	}
	/**
	 * 开始时间
	 * @return the historyStepStartTime
	 */
	public String getHistoryStepStartTime() {
		return historyStepStartTime;
	}
	/**
	 * 开始时间
	 * @param historyStepStartTime the historyStepStartTime to set
	 */
	public void setHistoryStepStartTime(String historyStepStartTime) {
		this.historyStepStartTime = historyStepStartTime;
	}
	/**
	 * 结束时间
	 * @return the historyStepEndTime
	 */
	public String getHistoryStepEndTime() {
		return historyStepEndTime;
	}
	/**
	 * 结束时间
	 * @param historyStepEndTime the historyStepEndTime to set
	 */
	public void setHistoryStepEndTime(String historyStepEndTime) {
		this.historyStepEndTime = historyStepEndTime;
	}
	/**
	 * 步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @return the historyStepJobStatus
	 */
	public String getHistoryStepJobStatus() {
		return historyStepJobStatus;
	}
	/**
	 * 步骤状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param historyStepJobStatus the historyStepJobStatus to set
	 */
	public void setHistoryStepJobStatus(String historyStepJobStatus) {
		this.historyStepJobStatus = historyStepJobStatus;
	}
	/**
	 * 步骤错误信息
	 * @return the historyStepJobErrmsg
	 */
	public String getHistoryStepJobErrmsg() {
		return historyStepJobErrmsg;
	}
	/**
	 * 步骤错误信息
	 * @param historyStepJobErrmsg the historyStepJobErrmsg to set
	 */
	public void setHistoryStepJobErrmsg(String historyStepJobErrmsg) {
		this.historyStepJobErrmsg = historyStepJobErrmsg;
	}
	/**
	 * 关联作业ID
	 * @return the historyStepAssociateJobId
	 */
	public Long getHistoryStepAssociateJobId() {
		return historyStepAssociateJobId;
	}
	/**
	 * 关联作业ID
	 * @param historyStepAssociateJobId the historyStepAssociateJobId to set
	 */
	public void setHistoryStepAssociateJobId(Long historyStepAssociateJobId) {
		this.historyStepAssociateJobId = historyStepAssociateJobId;
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
		return "BatchTimingStepLogHistory[" + 
		"historyStepExecutionId=" + historyStepExecutionId + ", historyStepJobId=" + historyStepJobId + ", historyStepJobName=" + historyStepJobName + ", historyStepStartTime=" + historyStepStartTime + 
		", historyStepEndTime=" + historyStepEndTime + ", historyStepJobStatus=" + historyStepJobStatus + ", historyStepJobErrmsg=" + historyStepJobErrmsg + 
		", historyStepAssociateJobId=" + historyStepAssociateJobId + ", createDate=" + createDate + ", validStatus=" + validStatus + 
		", remark=" + remark + "]";
	}
}