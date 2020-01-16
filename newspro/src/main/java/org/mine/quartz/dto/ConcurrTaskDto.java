package org.mine.quartz.dto;

import java.util.HashMap;
import java.util.Map;

import org.mine.aplt.util.CommonUtils;

/**
 * 任务执行DTO
 * @filename ConcurrTaskDto.java 
 * @author wzaUsers
 * @date 2019年12月31日上午10:21:42 
 * @version v1.0
 */
public class ConcurrTaskDto {
	/**
	 * 组别ID
	 */
	private Long groupId;
	/**
	 * 当前组别是否记录日志
	 */
	private Integer groupSaveLog;
	/**
	 * 当前作业是否一次性任务
	 */
	private Integer jobOneTime;
	/**
	 * 当前任务ID
	 */
	private Long taskId;
	/**
	 * 当前任务跳过标志
	 */
	private Integer taskSkipFlag;
	/**
	 * 当前任务初始值
	 */
	private Map<String, Object> taskInitValue;
	/**
	 * 当前作业初始化值
	 */
	private Map<String, Object> jobInitValue;
	/**
	 * 当前作业可跳过标志
	 */
	private Integer jobSkipFlag;
	/**
	 * 当前作业多次执行标志
	 */
	private Integer jobRunMutiFlag;
	/**
	 * 当前作业延时执行标志
	 */
	private Integer jobTimeDelayFlag;
	/**
	 * 当前作业延时执行值
	 */
	private String jobTimeDelayValue;
	/**
	 * 当前执行作业
	 */
	private Long jobId;
	/**
	 * 当前执行作业名称
	 */
	private String jobName;
	/**
	 * 当前步骤日志文件名
	 */
	private String stepMdcValue;
	/**
	 * 当前步骤初始化值
	 */
	private Map<String, Object> stepInitValue;
	/**
	 * 历史执行ID
	 */
	private Long historyId;
	
	public ConcurrTaskDto() {
		super();
		this.groupId = 0L;
		this.groupSaveLog = 0;
		this.jobOneTime = 0;
		this.taskId = 0L;
		this.taskSkipFlag = 0;
		this.taskInitValue = new HashMap<>();
		this.jobInitValue = new HashMap<>();
		this.jobSkipFlag = 0;
		this.jobRunMutiFlag = 0;
		this.jobTimeDelayFlag = 0;
		this.jobTimeDelayValue = "";
		this.jobId = 0L;
		this.jobName = "";
		this.stepMdcValue = "";
		this.stepInitValue = new HashMap<>();
		this.historyId = 0L;
	}
	/**
	 * 组别ID
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * 组别ID
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * 当前组别是否记录日志
	 * @return the groupSaveLog
	 */
	public Integer getGroupSaveLog() {
		return groupSaveLog;
	}
	/**
	 * 当前组别是否记录日志
	 * @param groupSaveLog the groupSaveLog to set
	 */
	public void setGroupSaveLog(Integer groupSaveLog) {
		this.groupSaveLog = groupSaveLog;
	}
	/**
	 * 当前作业是否一次性任务
	 * @return the groupOneTime
	 */
	public Integer getJobOneTime() {
		return jobOneTime;
	}
	/**
	 * 当前作业是否一次性任务
	 * @param groupOneTime the groupOneTime to set
	 */
	public void setJobOneTime(Integer jobOneTime) {
		this.jobOneTime = jobOneTime;
	}
	/**
	 * 当前任务ID
	 * @return the taskId
	 */
	public Long getTaskId() {
		return taskId;
	}
	/**
	 * 当前任务ID
	 * @param taskId the taskId to set
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	/**
	 * 当前任务跳过标志
	 * @return the taskSkipFlag
	 */
	public Integer getTaskSkipFlag() {
		return taskSkipFlag;
	}
	/**
	 * 当前任务跳过标志
	 * @param taskSkipFlag the taskSkipFlag to set
	 */
	public void setTaskSkipFlag(Integer taskSkipFlag) {
		this.taskSkipFlag = taskSkipFlag;
	}
	/**
	 * 当前任务初始值
	 * @return the taskInitValue
	 */
	public Map<String, Object> getTaskInitValue() {
		return taskInitValue;
	}
	/**
	 * 当前任务初始值
	 * @param taskInitValue the taskInitValue to set
	 */
	public void setTaskInitValue(Map<String, Object> taskInitValue) {
		this.taskInitValue = taskInitValue;
	}
	/**
	 * 当前作业初始化值
	 * @return the jobInitValue
	 */
	public Map<String, Object> getJobInitValue() {
		return jobInitValue;
	}
	/**
	 * 当前作业初始化值
	 * @param jobInitValue the jobInitValue to set
	 */
	public void setJobInitValue(Map<String, Object> jobInitValue) {
		this.jobInitValue = jobInitValue;
	}
	/**
	 * 当前作业可跳过标志
	 * @return the jobSkipFlag
	 */
	public Integer getJobSkipFlag() {
		return jobSkipFlag;
	}
	/**
	 * 当前作业可跳过标志
	 * @param jobSkipFlag the jobSkipFlag to set
	 */
	public void setJobSkipFlag(Integer jobSkipFlag) {
		this.jobSkipFlag = jobSkipFlag;
	}
	/**
	 * 当前作业多次执行标志
	 * @return the jobRunMutiFlag
	 */
	public Integer getJobRunMutiFlag() {
		return jobRunMutiFlag;
	}
	/**
	 * 当前作业多次执行标志
	 * @param jobRunMutiFlag the jobRunMutiFlag to set
	 */
	public void setJobRunMutiFlag(Integer jobRunMutiFlag) {
		this.jobRunMutiFlag = jobRunMutiFlag;
	}
	/**
	 * 当前作业延时执行标志
	 * @return the jobTimeDelayFlag
	 */
	public Integer getJobTimeDelayFlag() {
		return jobTimeDelayFlag;
	}
	/**
	 * 当前作业延时执行标志
	 * @param jobTimeDelayFlag the jobTimeDelayFlag to set
	 */
	public void setJobTimeDelayFlag(Integer jobTimeDelayFlag) {
		this.jobTimeDelayFlag = jobTimeDelayFlag;
	}
	/**
	 * 当前作业延时执行值
	 * @return the jobTimeDelayValue
	 */
	public String getJobTimeDelayValue() {
		return jobTimeDelayValue;
	}
	/**
	 * 当前作业延时执行值
	 * @param jobTimeDelayValue the jobTimeDelayValue to set
	 */
	public void setJobTimeDelayValue(String jobTimeDelayValue) {
		this.jobTimeDelayValue = jobTimeDelayValue;
	}
	/**
	 * 当前执行作业
	 * @return the jobId
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * 当前执行作业
	 * @param jobId the jobId to set
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * 当前执行作业名称
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * 当前执行作业名称
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * 当前步骤日志文件名
	 * @return the stepMdcValue
	 */
	public String getStepMdcValue() {
		return stepMdcValue;
	}
	/**
	 * 当前步骤日志文件名
	 * @param stepMdcValue the stepMdcValue to set
	 */
	public void setStepMdcValue(String stepMdcValue) {
		this.stepMdcValue = stepMdcValue;
	}
	/**
	 * 当前步骤初始化值
	 * @return the stepInitValue
	 */
	public Map<String, Object> getStepInitValue() {
		return stepInitValue;
	}
	/**
	 * 当前步骤初始化值
	 * @param stepInitValue the stepInitValue to set
	 */
	public void setStepInitValue(Map<String, Object> stepInitValue) {
		this.stepInitValue = stepInitValue;
	}
	/**
	 * 历史执行ID
	 * @return the historyId
	 */
	public Long getHistoryId() {
		return historyId;
	}
	/**
	 * 历史执行ID
	 * @param historyId the historyId to set
	 */
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConcurrTaskDto [groupId= " + groupId + ", groupSaveLog=" + groupSaveLog + ", jobOneTime="
				+ jobOneTime + ", taskId=" + taskId + ", taskSkipFlag=" + taskSkipFlag + ", taskInitValue="
				+ CommonUtils.toString(taskInitValue) + ", jobInitValue=" + CommonUtils.toString(jobInitValue)
				+ ", jobSkipFlag=" + jobSkipFlag + ", jobRunMutiFlag=" + jobRunMutiFlag + ", jobTimeDelayFlag="
				+ jobTimeDelayFlag + ", jobTimeDelayValue=" + jobTimeDelayValue + ", jobId=" + jobId + ", jobName="
				+ jobName + ", stepMdcValue=" + stepMdcValue + ", stepInitValue=" + CommonUtils.toString(stepInitValue)
				+ ", historyId=" + historyId + "]";
	}
}
