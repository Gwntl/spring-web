package org.mine.quartz.dto;

/**
 * @Description: 监视器Dto
 * @ClassName: MonitorDto
 * @author: wntl
 * @date: 2020年4月29日 下午8:34:53
 */
public class MonitorDto {
	/**
	 * 作业ID
	 */
	private String jobID;
	/**
	 * JOB任务实例
	 */
	private String jobExecutionInstance;
	/**
	 * TASK任务实例
	 */
	private String taskExecutionInstance;
	/**
	 * QUEUE任务实例
	 */
	private String queueExecutionInstance;
	/**
	 * 指定并发数
	 */
	private int concurrencyNum;
	/**
	 * job运行状态
	 */
	private String jobStatus;

	/**
	 * @return the jobID as $field.comment
	 */
	public String getJobID() {
		return jobID;
	}

	/**
	 * @param jobID the jobID to set
	 */
	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	/**
	 * JOB任务实例
	 * @return the jobExecutionInstance
	 */
	public String getJobExecutionInstance() {
		return jobExecutionInstance;
	}
	/**
	 * JOB任务实例
	 * @param jobExecutionInstance the jobExecutionInstance to set
	 */
	public void setJobExecutionInstance(String jobExecutionInstance) {
		this.jobExecutionInstance = jobExecutionInstance;
	}
	/**
	 * TASK任务实例
	 * @return the taskExecutionInstance
	 */
	public String getTaskExecutionInstance() {
		return taskExecutionInstance;
	}
	/**
	 * TASK任务实例
	 * @param taskExecutionInstance the taskExecutionInstance to set
	 */
	public void setTaskExecutionInstance(String taskExecutionInstance) {
		this.taskExecutionInstance = taskExecutionInstance;
	}
	/**
	 * QUEUE任务实例
	 * @return the queueExecutionInstance
	 */
	public String getQueueExecutionInstance() {
		return queueExecutionInstance;
	}
	/**
	 * QUEUE任务实例
	 * @param queueExecutionInstance the queueExecutionInstance to set
	 */
	public void setQueueExecutionInstance(String queueExecutionInstance) {
		this.queueExecutionInstance = queueExecutionInstance;
	}
	/**
	 * 指定并发数
	 * @return the concurrencyNum
	 */
	public int getConcurrencyNum() {
		return concurrencyNum;
	}
	/**
	 * 指定并发数
	 * @param concurrencyNum the concurrencyNum to set
	 */
	public void setConcurrencyNum(int concurrencyNum) {
		this.concurrencyNum = concurrencyNum;
	}

	/**
	 * job运行状态
	 * @return the jobStatus as $field.comment
	 */
	public String getJobStatus() {
		return jobStatus;
	}

	/**
	 * job运行状态
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	@Override
	public String toString() {
		return "MonitorDto{jobID=" + jobID + ", jobExecutionInstance=" + jobExecutionInstance
				+ ", taskExecutionInstance=" + taskExecutionInstance + ", queueExecutionInstance=" +
				queueExecutionInstance + ", jobStatus=" + jobStatus + "}";
	}
}
