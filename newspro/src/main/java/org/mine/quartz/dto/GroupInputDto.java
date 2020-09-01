package org.mine.quartz.dto;

import org.mine.aplt.util.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GroupInputDto implements Serializable{
	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 496109499635930535L;

	private Map<String, Object> stepInitValue;
	
	private Map<String, Object> jobInitValue;
	
	private Map<String, Object> taskInitValue;
	
	private String jobId;
	
	private String stepId;
	
	private String stepActuator;
	
	public GroupInputDto() {
		super();
		this.stepInitValue = new HashMap<>();
		this.jobInitValue = new HashMap<>();
		this.taskInitValue = new HashMap<>();
		this.jobId = "";
		this.stepId = "";
		this.stepActuator = "";
	}

	/**
	 * @return the stepInitValue
	 */
	public Map<String, Object> getStepInitValue() {
		return stepInitValue;
	}

	/**
	 * @param stepInitValue the stepInitValue to set
	 */
	public void setStepInitValue(Map<String, Object> stepInitValue) {
		this.stepInitValue = stepInitValue;
	}

	/**
	 * @return the jobInitValue
	 */
	public Map<String, Object> getJobInitValue() {
		return jobInitValue;
	}

	/**
	 * @param jobInitValue the jobInitValue to set
	 */
	public void setJobInitValue(Map<String, Object> jobInitValue) {
		this.jobInitValue = jobInitValue;
	}

	/**
	 * @return the taskInitValue
	 */
	public Map<String, Object> getTaskInitValue() {
		return taskInitValue;
	}

	/**
	 * @param taskInitValue the taskInitValue to set
	 */
	public void setTaskInitValue(Map<String, Object> taskInitValue) {
		this.taskInitValue = taskInitValue;
	}

	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the stepId
	 */
	public String getStepId() {
		return stepId;
	}

	/**
	 * @param stepId the stepId to set
	 */
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	/**
	 * @return the stepActuator
	 */
	public String getStepActuator() {
		return stepActuator;
	}

	/**
	 * @param stepActuator the stepActuator to set
	 */
	public void setStepActuator(String stepActuator) {
		this.stepActuator = stepActuator;
	}

	/**
	 * <p>Description: </p>
	 * <p>Title: toString</p>
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GroupInputDto [stepInitValue="
				+ (CommonUtils.isEmpty(stepInitValue) ? null : CommonUtils.toString(stepInitValue)) + ", jobInitValue="
				+ (CommonUtils.isEmpty(jobInitValue) ? null : CommonUtils.toString(jobInitValue)) + ", taskInitValue="
				+ (CommonUtils.isEmpty(taskInitValue) ? null : CommonUtils.toString(taskInitValue)) + ", jobId=" + jobId
				+ ", stepId=" + stepId + ", stepActuator=" + stepActuator + "]";
	}
}
