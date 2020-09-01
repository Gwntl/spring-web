package org.mine.quartz.dto;

import org.mine.aplt.util.CommonUtils;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: TaskExecuteDto
 * @date 2020/8/269:02
 */
public class TaskExecuteDto {
    /**
     * JOB执行实例队列. 非线程安全, 使用双向链表.
     */
    private LinkedList<String> jobInstances;
    /**
     * JOB执行中信息缓存
     */
    private Map<String, TaskInnerDto> innerDtoMap;
    /**
     * Task执行成功标志.
     */
    private boolean taskSuccessFlag;

    public TaskExecuteDto() {
        this.jobInstances = new LinkedList<>();
        this.innerDtoMap = new ConcurrentHashMap<>();
        this.taskSuccessFlag = true;
    }

    /**
     * JOB执行实例队列
     * @return the jobInstances as $field.comment
     */
    public LinkedList<String> getJobInstances() {
        return jobInstances;
    }

    /**
     * JOB执行实例队列
     * @param jobInstances the jobInstances to set
     */
    public void setJobInstances(LinkedList<String> jobInstances) {
        this.jobInstances = jobInstances;
    }

    /**
     * JOB执行中信息缓存
     * @return the innerDtoMap as $field.comment
     */
    public Map<String, TaskInnerDto> getInnerDtoMap() {
        return innerDtoMap;
    }

    /**
     * JOB执行中信息缓存
     * @param innerDtoMap the innerDtoMap to set
     */
    public void setInnerDtoMap(Map<String, TaskInnerDto> innerDtoMap) {
        this.innerDtoMap = innerDtoMap;
    }

    /**
     * Task执行成功标志
     * @return the taskSuccessFlag as $field.comment
     */
    public boolean isTaskSuccessFlag() {
        return taskSuccessFlag;
    }

    /**
     * Task执行成功标志
     * @param taskSuccessFlag the taskSuccessFlag to set
     */
    public void setTaskSuccessFlag(boolean taskSuccessFlag) {
        this.taskSuccessFlag = taskSuccessFlag;
    }

    public static class TaskInnerDto {
        /**
         * 任务ID
         */
        private String taskID;
        /**
         * 作业ID
         */
        private String jobID;
        /**
         * 当前执行JOB实例
         */
        private String currentJobInstance;
        /**
         * 是否一次性任务. 表示在当前TASK执行配置中.
         */
        private boolean oneTimeFlag;
        /**
         * 是否依赖其他JOB
         */
        private boolean dependsFlag;
        /**
         * 依赖JOB
         */
        private LinkedList<String> dependsJob;
        /**
         * 是否被其他作业依赖
         */
        private boolean beDependsFlag;
        /**
         * 被依赖JOB
         */
        private LinkedList<String> beDependsJob;
        /**
         * 当前JOB执行DTO对象信息保存
         */
        private ExecuteTaskDto taskDto;

        public TaskInnerDto(){
            this.taskID = "";
            this.jobID = "";
            this.currentJobInstance = "";
            this.oneTimeFlag = false;
            this.dependsFlag = false;
            this.dependsJob = new LinkedList<>();
            this.beDependsFlag = false;
            this.beDependsJob = new LinkedList<>();
            this.taskDto = new ExecuteTaskDto();
        }

        /**
         * @return the taskID as $field.comment
         */
        public String getTaskID() {
            return taskID;
        }

        /**
         * @param taskID the taskID to set
         */
        public void setTaskID(String taskID) {
            this.taskID = taskID;
        }

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
         * @return the currentJobInstance as $field.comment
         */
        public String getCurrentJobInstance() {
            return currentJobInstance;
        }

        /**
         * @param currentJobInstance the currentJobInstance to set
         */
        public void setCurrentJobInstance(String currentJobInstance) {
            this.currentJobInstance = currentJobInstance;
        }

        /**
         * @return the oneTimeFlag as $field.comment
         */
        public boolean isOneTimeFlag() {
            return oneTimeFlag;
        }

        /**
         * @param oneTimeFlag the oneTimeFlag to set
         */
        public void setOneTimeFlag(boolean oneTimeFlag) {
            this.oneTimeFlag = oneTimeFlag;
        }

        /**
         * @return the dependsFlag as $field.comment
         */
        public boolean isDependsFlag() {
            return dependsFlag;
        }

        /**
         * @param dependsFlag the dependsFlag to set
         */
        public void setDependsFlag(boolean dependsFlag) {
            this.dependsFlag = dependsFlag;
        }

        /**
         * @return the dependsJob as $field.comment
         */
        public LinkedList<String> getDependsJob() {
            return dependsJob;
        }

        /**
         * @param dependsJob the dependsJob to set
         */
        public void setDependsJob(LinkedList<String> dependsJob) {
            this.dependsJob = dependsJob;
        }

        /**
         * @return the beDependsFlag as $field.comment
         */
        public boolean isBeDependsFlag() {
            return beDependsFlag;
        }

        /**
         * @param beDependsFlag the beDependsFlag to set
         */
        public void setBeDependsFlag(boolean beDependsFlag) {
            this.beDependsFlag = beDependsFlag;
        }

        /**
         * @return the beDependsJob as $field.comment
         */
        public LinkedList<String> getBeDependsJob() {
            return beDependsJob;
        }

        /**
         * @param beDependsJob the beDependsJob to set
         */
        public void setBeDependsJob(LinkedList<String> beDependsJob) {
            this.beDependsJob = beDependsJob;
        }

        /**
         * @return the taskDto as $field.comment
         */
        public ExecuteTaskDto getTaskDto() {
            return taskDto;
        }

        /**
         * @param taskDto the taskDto to set
         */
        public void setTaskDto(ExecuteTaskDto taskDto) {
            this.taskDto = taskDto;
        }

        @Override
        public String toString() {
            return "TaskInnerDto{TaskID=" + taskID + ", jobID=" + jobID + ", currentJobInstance=" + currentJobInstance
                    + ", " + "oneTimeFlag=" + oneTimeFlag + ", dependsFlag=" + dependsFlag
                    + ", dependsJob=" + (dependsJob != null ? CommonUtils.toString(dependsJob): null)
                    + "," + " beDependsFlag=" + beDependsFlag
                    + ", beDependsJob=" + (beDependsJob != null ? CommonUtils.toString(beDependsJob): null)
                    + ", taskDto=" + (taskDto == null ? null : taskDto.toString()) + '}';
        }
    }

    @Override
    public String toString() {
        return "TaskExecuteDto{jobInstances=" + (jobInstances == null ? null : CommonUtils.toString(jobInstances))
                + ", innerDtoMap=" + (innerDtoMap != null ? CommonUtils.toString(innerDtoMap) : "")
                + ", taskSuccessFlag=" + taskSuccessFlag + '}';
    }
}
