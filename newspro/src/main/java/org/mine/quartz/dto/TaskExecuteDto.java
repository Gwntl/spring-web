package org.mine.quartz.dto;

import org.mine.aplt.constant.JobConstant;
import org.mine.aplt.util.CommonUtils;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
     * 待执行作业队列. 非线程安全, 使用双向链表.
     */
    private LinkedList<String> pendingJobs;
    /**
     * Task执行成功标志.
     */
    private boolean taskSuccessFlag;
    /**
     * 停止标志. 0-正常, 1-停止, 2-取消.
     */
    private AtomicInteger taskStatusFlag;

    public TaskExecuteDto() {
        this.jobInstances = new LinkedList<>();
        this.innerDtoMap = new ConcurrentHashMap<>();
        this.pendingJobs = new LinkedList<>();
        this.taskSuccessFlag = true;
        this.taskStatusFlag = new AtomicInteger(JobConstant.TASK_STATUS_0);
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
     * 待执行作业队列.
     * @return the pendingJobs as pendingJobs
     */
    public LinkedList<String> getPendingJobs() {
        return pendingJobs;
    }

    /**
     * 待执行作业队列.
     * @param pendingJobs the pendingJobs to set
     */
    public void setPendingJobs(LinkedList<String> pendingJobs) {
        this.pendingJobs = pendingJobs;
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

    /**
     * 停止标志
     * @return the taskStatusFlag as taskStatusFlag
     */
    public Integer getTaskStatusFlag() {
        return taskStatusFlag.get();
    }

    /**
     * 停止标志
     * @param taskStatusFlag the taskStatusFlag to set
     */
    public boolean setTaskStatusFlag(Integer expectValue, Integer taskStatusFlag) {
        return this.taskStatusFlag.compareAndSet(expectValue, taskStatusFlag);
    }

    /**
     * 判断是否停止
     * @return
     */
    public boolean isTaskStatusAbnormal() {
        return taskStatusFlag.get() != 0;
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
         * 执行次数
         */
        private Integer executeTimes;
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
        /**
         * 当前JOB执行中标志
         */
        private boolean executingFlag;

        public TaskInnerDto(){
            this.taskID = "";
            this.jobID = "";
            this.currentJobInstance = "";
            this.executeTimes = 0;
            this.dependsFlag = false;
            this.dependsJob = new LinkedList<>();
            this.beDependsFlag = false;
            this.beDependsJob = new LinkedList<>();
            this.taskDto = new ExecuteTaskDto();
            this.executingFlag = false;
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
         * 执行次数
         * @return the executeTimes as $field.comment
         */
        public Integer getExecuteTimes() {
            return executeTimes;
        }

        /**
         * 执行次数
         * @param executeTimes the executeTimes to set
         */
        public void setExecuteTimes(Integer executeTimes) {
            this.executeTimes = executeTimes;
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

        /**
         * 当前JOB执行中标志
         * @return the executingFlag as $field.comment
         */
        public boolean isExecutingFlag() {
            return executingFlag;
        }

        /**
         * 当前JOB执行中标志
         * @param executingFlag the executingFlag to set
         */
        public void setExecutingFlag(boolean executingFlag) {
            this.executingFlag = executingFlag;
        }

        @Override
        public String toString() {
            return "TaskInnerDto{TaskID=" + taskID + ", jobID=" + jobID + ", currentJobInstance=" + currentJobInstance
                    + ", executeTimes=" + executeTimes + ", dependsFlag=" + dependsFlag
                    + ", dependsJob=" + (dependsJob != null ? CommonUtils.toString(dependsJob): null)
                    + "," + " beDependsFlag=" + beDependsFlag
                    + ", beDependsJob=" + (beDependsJob != null ? CommonUtils.toString(beDependsJob): null)
                    + ", taskDto=" + (taskDto == null ? null : taskDto.toString()) + ", executingFlag=" + executingFlag
                    + '}';
        }
    }

    @Override
    public String toString() {
        return "TaskExecuteDto{jobInstances=" + (jobInstances == null ? null : CommonUtils.toString(jobInstances))
                + ", innerDtoMap=" + (innerDtoMap != null ? CommonUtils.toString(innerDtoMap) : "")
                + ", taskSuccessFlag=" + taskSuccessFlag + ", taskStopFlag=" + taskStatusFlag.get() + '}';
    }
}
