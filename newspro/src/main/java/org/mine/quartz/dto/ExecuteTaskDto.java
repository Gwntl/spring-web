package org.mine.quartz.dto;

import org.mine.aplt.util.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务执行DTO
 * @filename ExecuteTaskDto.java
 * @author wzaUsers
 * @date 2019年12月31日上午10:21:42 
 * @version v1.0
 */
public class ExecuteTaskDto implements Serializable, Cloneable{
	private static final long serialVersionUID = 428743667114755935L;
	/**
	 * 当前队列初始值
	 */
	private Map<String, Object> queueInitValue;
	/**
	 * 队列ID
	 */
	private String queueId;
	/**
	 * 队列名称
	 */
	private String queueName;
	/**
	 * 当前组别是否记录日志
	 */
	private Integer jobLogFlag;
	/**
	 * 当前作业是否一次性任务
	 */
	private Integer jobOneTime;
	/**
	 * 当前任务ID
	 */
	private String taskId;
	/**
	 * 当前任务名称
	 */
	private String taskName;
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
	private String jobId;
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
	 * 执行实例
	 */
	private String executionInstance;
	/**
	 * Task执行实例
	 */
	private String taskExecutionInstance;
	/**
	 * Queue执行实例
	 */
	private String queueExecutionInstance;
	/**
	 * 指定并发数
	 */
	private int concurrencyNum;
	/**
	 * 已执行成功JOB
	 */
	private Map<String, Object> successJobMap;
	/**
	 * 重启标志
	 */
	private boolean restartTaskFlag;

	public ExecuteTaskDto() {
		super();
		this.queueInitValue = new HashMap<>();
		this.queueId = "";
		this.queueName = "";
		this.jobLogFlag = 0;
		this.jobOneTime = 0;
		this.taskId = "";
		this.taskName = "";
		this.taskSkipFlag = 0;
		this.taskInitValue = new HashMap<>();
		this.jobInitValue = new HashMap<>();
		this.jobSkipFlag = 0;
		this.jobRunMutiFlag = 0;
		this.jobTimeDelayFlag = 0;
		this.jobTimeDelayValue = "";
		this.jobId = "";
		this.jobName = "";
		this.stepMdcValue = "";
		this.stepInitValue = new HashMap<>();
		this.executionInstance = "";
		this.taskExecutionInstance = "";
		this.queueExecutionInstance = "";
		this.concurrencyNum = 0;
		this.successJobMap = new HashMap<>();
		this.restartTaskFlag = false;
	}
	
	/**
	 * 当前队列初始值
	 * @return the queueInitValue
	 */
	public Map<String, Object> getQueueInitValue() {
		return queueInitValue;
	}

	/**
	 * 当前队列初始值
	 * @param queueInitValue the queueInitValue to set
	 */
	public void setQueueInitValue(Map<String, Object> queueInitValue) {
		this.queueInitValue = queueInitValue;
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
	 * 队列名称
	 * @return the queueName as $field.comment
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * 队列名称
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * 当前组别是否记录日志
	 * @return the groupSaveLog
	 */
	public Integer getJobLogFlag() {
		return jobLogFlag;
	}
	/**
	 * 当前组别是否记录日志
	 * @param groupSaveLog the groupSaveLog to set
	 */
	public void setJobLogFlag(Integer groupSaveLog) {
		this.jobLogFlag = groupSaveLog;
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
	 * @param jobOneTime the jobOneTime to set
	 */
	public void setJobOneTime(Integer jobOneTime) {
		this.jobOneTime = jobOneTime;
	}
	/**
	 * 当前任务ID
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}
	/**
	 * 当前任务ID
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/**
	 * 当前任务名称
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * 当前任务名称
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
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
	public String getJobId() {
		return jobId;
	}
	/**
	 * 当前执行作业
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
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
	 * Job执行实例
	 * @return the executionInstance
	 */
	public String getExecutionInstance() {
		return executionInstance;
	}
	/**
	 * Job执行实例
	 * @param executionInstance the executionInstance to set
	 */
	public void setExecutionInstance(String executionInstance) {
		this.executionInstance = executionInstance;
	}
	/**
	 * Task执行实例
	 * @return the taskExecutionInstance
	 */
	public String getTaskExecutionInstance() {
		return taskExecutionInstance;
	}
	/**
	 * Task执行实例
	 * @param taskExecutionInstance the taskExecutionInstance to set
	 */
	public void setTaskExecutionInstance(String taskExecutionInstance) {
		this.taskExecutionInstance = taskExecutionInstance;
	}

	/**
	 * @return the queueExecutionInstance as $field.comment
	 */
	public String getQueueExecutionInstance() {
		return queueExecutionInstance;
	}

	/**
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
	 * 已执行成功JOB
	 * @return the successJobMap as successJobMap
	 */
	public Map<String, Object> getSuccessJobMap() {
		return successJobMap;
	}

	/**
	 * 已执行成功JOB
	 * @param successJobMap the successJobMap to set
	 */
	public void setSuccessJobMap(Map<String, Object> successJobMap) {
		this.successJobMap = successJobMap;
	}

	/**
	 * 重启标志
	 * @return the restartTaskFlag as restartTaskFlag
	 */
	public boolean getRestartTaskFlag() {
		return restartTaskFlag;
	}

	/**
	 * 重启标志
	 * @param restartTaskFlag the restartTaskFlag to set
	 */
	public void setRestartTaskFlag(boolean restartTaskFlag) {
		this.restartTaskFlag = restartTaskFlag;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		ExecuteTaskDto cloneDto = (ExecuteTaskDto)super.clone();
		if (this.queueInitValue != null) {
			cloneDto.setQueueInitValue((HashMap<String, Object>)((HashMap<String, Object>)this.queueInitValue).clone());
		}
		if (this.taskInitValue != null) {
			cloneDto.setTaskInitValue((HashMap<String, Object>)((HashMap<String, Object>)this.taskInitValue).clone());
		}
		if (this.jobInitValue != null) {
			cloneDto.setJobInitValue((HashMap<String, Object>)((HashMap<String, Object>)this.jobInitValue).clone());
		}
		if (this.stepInitValue != null) {
			cloneDto.setStepInitValue((HashMap<String, Object>)((HashMap<String, Object>)this.stepInitValue).clone());
		}
		if (this.successJobMap != null) {
			cloneDto.setStepInitValue((HashMap<String, Object>)((HashMap<String, Object>)this.successJobMap).clone());
		}
		return cloneDto;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExecuteTaskDto [queueInitValue = " + CommonUtils.toString(queueInitValue) + ", queueId= " + queueId + ", queueName=" + queueName
				+ ", groupSaveLog=" + jobLogFlag + ", jobOneTime=" + jobOneTime + ", taskId=" + taskId + ", taskName=" + taskName
				+ ", taskSkipFlag=" + taskSkipFlag + ", taskInitValue=" + CommonUtils.toString(taskInitValue)
				+ ", jobInitValue=" + CommonUtils.toString(jobInitValue) + ", jobSkipFlag=" + jobSkipFlag
				+ ", jobRunMutiFlag=" + jobRunMutiFlag + ", jobTimeDelayFlag=" + jobTimeDelayFlag
				+ ", jobTimeDelayValue=" + jobTimeDelayValue + ", jobId=" + jobId + ", jobName=" + jobName
				+ ", stepMdcValue=" + stepMdcValue + ", stepInitValue=" + CommonUtils.toString(stepInitValue)
				+ ", executionInstance=" + executionInstance + ", taskExecutionInstance=" + taskExecutionInstance
				+ ", queueExecutionInstance=" + queueExecutionInstance + ", concurrencyNum=" + concurrencyNum
				+ "，successJobMap=" + CommonUtils.toString(successJobMap) + ", restartTaskFlag=" + restartTaskFlag + "]";
	}

	public static void main(String[] args) throws Exception{
		ExecuteTaskDto taskDto = new ExecuteTaskDto();
		taskDto.setTaskName("names");
		taskDto.setJobId("1001");
		HashMap map = new HashMap();
		map.put("key", "value");
		map.put("key1", "value1");
		taskDto.setJobInitValue(map);
		ExecuteTaskDto taskDto1 = taskDto;
//		ExecuteTaskDto dto_clone = BeanUtil.deepClone(taskDto);
		ExecuteTaskDto dto_clone = (ExecuteTaskDto)taskDto.clone();
		System.out.println(taskDto.toString() + ", " + taskDto1.toString() + ", " + dto_clone.toString());
		taskDto1.setTaskName("names_bak");
		taskDto1.getJobInitValue().put("key2", "value2");
		dto_clone.setTaskName("names_clone");
		dto_clone.getJobInitValue().put("key_clone", "value_clone");
		System.out.println(taskDto.toString() + ", " + taskDto1.toString() + ", " + dto_clone.toString());
	}
}
