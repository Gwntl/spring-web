package org.mine.quartz.dto;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: RestartTaskDto
 * @date 2020/9/249:29
 */
public class RestartTaskDto {
    /**
     * 任务ID
     */
    private String executeTaskId;
    /**
     * 作业ID
     */
    private String executeJobId;
    /**
     * 作业执行次数
     */
    private int executeJobTimes;
    /**
     * 执行作业依赖项
     */
    private String executeJobDepends;
    /**
     * 作业执行实例
     */
    private String executionInstance;

    /**
     * @return the executeTaskId as executeTaskId
     */
    public String getExecuteTaskId() {
        return executeTaskId;
    }

    /**
     * 任务ID
     * @param executeTaskId the executeTaskId to set
     */
    public void setExecuteTaskId(String executeTaskId) {
        this.executeTaskId = executeTaskId;
    }

    /**
     * 作业ID
     * @return the executeJobId as executeJobId
     */
    public String getExecuteJobId() {
        return executeJobId;
    }

    /**
     * 作业ID
     * @param executeJobId the executeJobId to set
     */
    public void setExecuteJobId(String executeJobId) {
        this.executeJobId = executeJobId;
    }

    /**
     * 作业执行次数
     * @return the executeJobTimes as executeJobTimes
     */
    public int getExecuteJobTimes() {
        return executeJobTimes;
    }

    /**
     * 作业执行次数
     * @param executeJobTimes the executeJobTimes to set
     */
    public void setExecuteJobTimes(int executeJobTimes) {
        this.executeJobTimes = executeJobTimes;
    }

    /**
     * 执行作业依赖项
     * @return the executeJobDepends as executeJobDepends
     */
    public String getExecuteJobDepends() {
        return executeJobDepends;
    }

    /**
     * 执行作业依赖项
     * @param executeJobDepends the executeJobDepends to set
     */
    public void setExecuteJobDepends(String executeJobDepends) {
        this.executeJobDepends = executeJobDepends;
    }

    /**
     * 作业执行实例
     * @return the executionInstance as executionInstance
     */
    public String getExecutionInstance() {
        return executionInstance;
    }

    /**
     * 作业执行实例
     * @param executionInstance the executionInstance to set
     */
    public void setExecutionInstance(String executionInstance) {
        this.executionInstance = executionInstance;
    }

    @Override
    public String toString() {
        return "RestartTaskDto{executeTaskId='" + executeTaskId + '\'' +
                ", executeJobId='" + executeJobId + '\'' +
                ", executeJobTimes='" + executeJobTimes + '\'' +
                ", executeJobDepends='" + executeJobDepends + '\'' +
                ", executionInstance='" + executionInstance + '\'' +
                '}';
    }
}
