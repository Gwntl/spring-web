package org.mine.model;

/**
 * scheduler_runner_history--队列运行历史
 * @filename SchedulerRunnerHistory.java
 * @author wzaUsers
 * @date 2019-11-28 20:11:23
 * @version v1.0
*/
public class SchedulerRunnerHistory {
	/**
	 * 运行序号
	 */
	private Long runnerId;
	/**
	 * JOB任务ID
	 */
	private Long jobId;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * JOB任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 */
	private String jobStatus;
	/**
	 * JOB任务错误信息
	 */
	private String jobErrmsg;
	/**
	 * 有效状态
	 */
	private String vaildStatus;

	public SchedulerRunnerHistory() {
		this.runnerId = 0L;
		this.jobId = 0L;
		this.startTime = null;
		this.endTime = null;
		this.jobStatus = "";
		this.jobErrmsg = "";
		this.vaildStatus = "0";
	}

	/**
	 * 运行序号
	 * @return therunnerId
	 */
	public Long getRunnerId() {
		return runnerId;
	}
	/**
	 * 运行序号
	 * @param runnerId the runnerId to set
	 */
	public void setRunnerId(Long runnerId) {
		this.runnerId = runnerId;
	}
	/**
	 * JOB任务ID
	 * @return thejobId
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * JOB任务ID
	 * @param jobId the jobId to set
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * 开始时间
	 * @return thestartTime
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
	 * @return theendTime
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
	 * JOB任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @return thejobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}
	/**
	 * JOB任务状态,COMPLETED-处理中, SUCCSS-成功, FAILED-失败,UNKOWN-非正常状态
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	/**
	 * JOB任务错误信息
	 * @return thejobErrmsg
	 */
	public String getJobErrmsg() {
		return jobErrmsg;
	}
	/**
	 * JOB任务错误信息
	 * @param jobErrmsg the jobErrmsg to set
	 */
	public void setJobErrmsg(String jobErrmsg) {
		this.jobErrmsg = jobErrmsg;
	}
	/**
	 * 有效状态
	 * @return thevaildStatus
	 */
	public String getVaildStatus() {
		return vaildStatus;
	}
	/**
	 * 有效状态
	 * @param vaildStatus the vaildStatus to set
	 */
	public void setVaildStatus(String vaildStatus) {
		this.vaildStatus = vaildStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SchedulerRunnerHistory[" + 
		"runnerId=" + runnerId + ", jobId=" + jobId + ", startTime=" + startTime + ", endTime=" + endTime + 
		", jobStatus=" + jobStatus + ", jobErrmsg=" + jobErrmsg + ", vaildStatus=" + vaildStatus + 
		"]";
	}
}