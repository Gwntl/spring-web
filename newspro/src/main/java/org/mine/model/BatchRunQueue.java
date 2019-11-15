package org.mine.model;

public class BatchRunQueue {
	/**
	 * 队列ID
	 * queue_id
	 */
	private String queueId;
	/**
	 * 队列名
	 * queue_name
	 */
	private String queueName;
	/**
	 * 开始时间
	 * queue_start_time
	 */
	private String queueStartTime;
	/**
	 * 结束时间
	 * queue_end_time
	 */
	private String queueEndTime;
	/**
	 * 触发器设置
	 * queue_crontrigger
	 */
	private String queueCrontrigger;
	/**
	 * 是否并发 0-并发 1-不并发
	 * queueIsConcurrent
	 */
	private String queueIsConcurrent;
	/**
	 * JOB作业ID 默认值"1001"
	 * queue_job_name
	 */
	private Long queueJobId;
	/**
	 * 备注
	 * remark
	 */
	private String queueRemark;
	
	public BatchRunQueue() {
		this.queueId = "";
		this.queueName = "";
		this.queueStartTime = null;
		this.queueEndTime = null;
		this.queueCrontrigger = "";
		this.queueIsConcurrent = "";
		this.queueJobId = 1001L;
		this.queueRemark = "";
	}
	/**
	 * 队列ID
	 * @return the queueId
	 */
	public String getQueueId() {
		return queueId;
	}

	/**
	 * 队列ID
	 * @param queueId the queueId to set
	 */
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	/**
	 * 队列名
	 * @return the queueName
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * 队列名
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * 开始时间
	 * @return the queueStartTime
	 */
	public String getQueueStartTime() {
		return queueStartTime;
	}

	/**
	 * 开始时间
	 * @param queueStartTime the queueStartTime to set
	 */
	public void setQueueStartTime(String queueStartTime) {
		this.queueStartTime = queueStartTime;
	}

	/**
	 * 结束时间
	 * @return the queueEndTime
	 */
	public String getQueueEndTime() {
		return queueEndTime;
	}

	/**
	 * 结束时间
	 * @param queueEndTime the queueEndTime to set
	 */
	public void setQueueEndTime(String queueEndTime) {
		this.queueEndTime = queueEndTime;
	}

	/**
	 * 触发器设置
	 * @return the queueCrontrigger
	 */
	public String getQueueCrontrigger() {
		return queueCrontrigger;
	}

	/**
	 * 触发器设置
	 * @param queueCrontrigger the queueCrontrigger to set
	 */
	public void setQueueCrontrigger(String queueCrontrigger) {
		this.queueCrontrigger = queueCrontrigger;
	}

	/**
	 * 任务执行初始值
	 * @return the queueIsConcurrent
	 */
	public String getQueueIsConcurrent() {
		return queueIsConcurrent;
	}
	/**
	 * 任务执行初始值
	 * @param queueIsConcurrent the queueIsConcurrent to set
	 */
	public void setQueueIsConcurrent(String queueIsConcurrent) {
		this.queueIsConcurrent = queueIsConcurrent;
	}
	/**
	 * JOB作业ID
	 * @return the queueJobId
	 */
	public Long getQueueJobId() {
		return queueJobId;
	}

	/**
	 * JOB作业ID
	 * @param queueJobId the queueJobId to set
	 */
	public void setQueueJobId(Long queueJobId) {
		this.queueJobId = queueJobId;
	}

	/**
	 * 备注
	 * @return the remark
	 */
	public String getQueueRemark() {
		return queueRemark;
	}

	/**
	 * 备注
	 * @param remark the remark to set
	 */
	public void setQueueRemark(String queueRemark) {
		this.queueRemark = queueRemark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchRunQueue [queueId=" + queueId + ", queueName=" + queueName + ", queueStartTime=" + queueStartTime
				+ ", queueEndTime=" + queueEndTime + ", queueCrontrigger=" + queueCrontrigger + ", queueIsConcurrent="
				+ queueIsConcurrent + ", queueJobId=" + queueJobId + ", queueRemark=" + queueRemark + "]";
	}
}
