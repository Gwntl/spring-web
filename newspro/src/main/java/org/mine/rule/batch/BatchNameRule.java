package org.mine.rule.batch;

import org.mine.aplt.constant.JobConstant;
import org.mine.rule.Ruler;

/**
*
* @filename: BatchNameRule
* @author wntl
* @date 2020/8/11 15:14
* @version v1.0
*/
public class BatchNameRule implements Ruler {
    /**
    * 队列命名
    * @param queueID
    * @return: void
    * @Author: wntl
    * @Date: 2020/8/11
    */
    public static String queueIDNamed(String queueID) {
        return JobConstant.QUEUE_ID_PREFIX + queueID;
    }

    /**
    * 执行ID命名
    * @param executeID
    * @return: java.lang.String
    * @Author: wntl
    * @Date: 2020/8/12
    */

    public static String executeNamed(String executeID) {
        return JobConstant.EXECUTE_PREFIX + executeID;
    }

    /**
    * 任务命名
    * @param taskID
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/8/11
    */
    public static String taskIDNamed(String taskID) {
        return JobConstant.TASK_ID_PREFIX + taskID;
    }

    /**
    * 作业命名
    * @param jobID
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/8/11
    */
    public static String jobIDNamed(String jobID) {
        return JobConstant.JOB_ID_PREFIX + jobID;
    }

    /**
    *
    * @param stepID
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/8/11
    */
    public static String stepIDNamed(String stepID){
        return JobConstant.STEP_ID_PREFIX + stepID;
    }

    /**
    *
    * @param triggerID
    * @return: java.lang.String
    * @Author: wntl
    * @Date: 2020/8/11
    */
    public static String triggerIDNamed(String triggerID){
        return JobConstant.TRIGGER_ID_PREFIX + triggerID;
    }

    public static String getType(String name) {
        String index = name.substring(0, name.indexOf("_"));
        switch (index) {
            case JobConstant.QUEUE_ID_PREFIX :
                return JobConstant.QUEUE_TYPE;
            case JobConstant.TASK_ID_PREFIX :
                return JobConstant.TASK_TYPE;
            case JobConstant.JOB_ID_PREFIX :
                return JobConstant.JOB_TYPE;
            case JobConstant.STEP_ID_PREFIX :
                return JobConstant.STEP_TYPE;
            default :
                return "";
        }
    }
}
