package org.mine.quartz.dto;

/**
 * @Description: 监视器Dto
 * @ClassName: JobMonitorDto
 * @author: wntl
 * @date: 2020年4月29日 下午8:34:53
 */
public class JobMonitorDto {
	/**
	 * 任务ID
	 */
	private Long jobId;
	/**
	 * 处理状态
	 */
	private String status;
	/**
	 * 历史ID
	 */
	private Long historyId;
	/**
	 * 一次性任务
	 */
	private int oneTime;
	/**
	 * 返回信息
	 */
	private String errmsg;
	/**
	 * 任务ID
	 * @return the taskId
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * 任务ID
	 * @param taskId the taskId to set
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * 处理状态
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 处理状态
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 历史ID
	 * @return the historyId
	 */
	public Long getHistoryId() {
		return historyId;
	}
	/**
	 * 历史ID
	 * @param historyId the historyId to set
	 */
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
	/**
	 * 一次性任务
	 * @return the oneTime
	 */
	public int getOneTime() {
		return oneTime;
	}
	/**
	 * 一次性任务
	 * @param oneTime the oneTime to set
	 */
	public void setOneTime(int oneTime) {
		this.oneTime = oneTime;
	}
	/**
	 * 返回信息
	 * @return the errmsg
	 */
	public String getErrmsg() {
		return errmsg;
	}
	/**
	 * 返回信息
	 * @param errmsg the errmsg to set
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	@Override
	public String toString() {
		return "JobMonitorDto [jobId=" + jobId + ", status=" + status + ", historyId=" + historyId + ", oneTime="
				+ oneTime + ", errmsg=" + errmsg + "]";
	}

}
