package org.mine.quartz;

/**
 * 任务所需值存放Model
 * @filename TaskRequiredValueStored.java 
 * @author wzaUsers
 * @date 2019年11月28日下午2:26:14 
 * @version v1.0
 */
public class TaskRepositoryValues {

	/**
	 * 是否记录日志
	 * isSaveLog
	 */
	private String isSaveLog;
	
	/**
	 * 运行ID
	 * runnerId
	 */
	private Long runnerId;
	
	/**
	 * JobID
	 * jobDetailId
	 */
	private Long jobDetailId;
	
	/**
	 * Job实体执行类
	 * jobDetailProvider
	 */
	private String jobDetailProvider;

	/**
	 * @return the isSaveLog
	 */
	public String getIsSaveLog() {
		return isSaveLog;
	}

	/**
	 * @param isSaveLog the isSaveLog to set
	 */
	public void setIsSaveLog(String isSaveLog) {
		this.isSaveLog = isSaveLog;
	}

	/**
	 * @return the runnerId
	 */
	public Long getRunnerId() {
		return runnerId;
	}

	/**
	 * @param runnerId the runnerId to set
	 */
	public void setRunnerId(Long runnerId) {
		this.runnerId = runnerId;
	}

	/**
	 * @return the jobDetailId
	 */
	public Long getJobDetailId() {
		return jobDetailId;
	}

	/**
	 * @param jobDetailId the jobDetailId to set
	 */
	public void setJobDetailId(Long jobDetailId) {
		this.jobDetailId = jobDetailId;
	}

	/**
	 * @return the jobDetailProvider
	 */
	public String getJobDetailProvider() {
		return jobDetailProvider;
	}

	/**
	 * @param jobDetailProvider the jobDetailProvider to set
	 */
	public void setJobDetailProvider(String jobDetailProvider) {
		this.jobDetailProvider = jobDetailProvider;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaskRepositoryValues [isSaveLog=" + isSaveLog + ", runnerId=" + runnerId + ", jobDetailId="
				+ jobDetailId + ", jobDetailProvider=" + jobDetailProvider + "]";
	}
}
