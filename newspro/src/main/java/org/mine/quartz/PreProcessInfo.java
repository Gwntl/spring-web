package org.mine.quartz;

import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: PreProcessInfo
 * @date 2020/8/3111:10
 */
public class PreProcessInfo {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 依赖JOB缓存.例：a依赖b. [a, {b}]
     */
    private static Map<String, Set<String>> dependOnJobMap = new ConcurrentHashMap<>(64);
    /**
     * 被依赖JOB缓存.例：a依赖b. [b, {a}]
     */
    private static Map<String, Set<String>> beDependOnJobMap = new ConcurrentHashMap<>(64);

    /**
     * @return the dependOnJobMap as $field.comment
     */
    public static Map<String, Set<String>> getDependOnJobMap() {
        return dependOnJobMap;
    }

    /**
     * @return the beDependOnJobMap as $field.comment
     */
    public static Map<String, Set<String>> getBeDependOnJobMap() {
        return beDependOnJobMap;
    }

    /**
    * 校验是否循环依赖.
    * @param jobID 检验JOB
    * @param dependentJobID 依赖JOB
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/8/31
    */
    public static boolean isDependent(String jobID, String dependentJobID) {
        return isDependent(jobID, dependentJobID, null);
    }

    /**
    * 校验是否循环依赖.
    * @param job 检验JOB
    * @param beDependsJob 依赖JOB
    * @param alreadySeen
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/8/31
    */
    public static boolean isDependent(String job, String beDependsJob, Set<String> alreadySeen) {
        if (alreadySeen != null && alreadySeen.contains(job)) {
            return false;
        }
        Set<String> dependents = beDependOnJobMap.get(job);
        if (dependents == null) {
            return false;
        }
        if (dependents.contains(beDependsJob)) {
            return true;
        }
        for (String dependent : dependents) {
            if (alreadySeen == null) {
                alreadySeen = new HashSet<>();
            }
            alreadySeen.add(job);
            if (isDependent(dependent, beDependsJob, alreadySeen)) {
                return true;
            }
        }
        return false;
    }

    /**
    * 根据JOB_ID获取依赖JOB信息
    * @param job
    * @return: java.util.Set<java.lang.String>
    * @Author: wntl
    * @Date: 2020/9/1
    */
    public static Set<String> getDependJob(String job) {
        if (job == null) {
            return null;
        }
        return dependOnJobMap.get(job);
    }

    /**
    * 根据JOB_ID获取被依赖JOB信息
    * @param beDependsJob
    * @return: java.util.Set<java.lang.String>
    * @Author: wntl
    * @Date: 2020/9/1
    */
    public static Set<String> getBeDependJob(String beDependsJob) {
        if (beDependsJob == null) {
            return null;
        }
        return beDependOnJobMap.get(beDependsJob);
    }

    public static void setDependJob(String job, String dependsJob) {
        if (CommonUtils.isEmpty(job)) {
            return;
        }

        Set<String> containedJobs = dependOnJobMap.get(job);
        if (containedJobs != null && containedJobs.contains(dependsJob)) {
            return;
        }
        List<String> beDepends = null;
        containedJobs = dependOnJobMap.get(job);
        synchronized (dependOnJobMap) {
            if (containedJobs == null) {
                containedJobs = new LinkedHashSet<String>(8);
                dependOnJobMap.put(job, containedJobs);
            }
            beDepends = CommonUtils.splitStrToList(dependsJob, ";", false);

            containedJobs.addAll(beDepends = CommonUtils.splitStrToList(dependsJob.trim(), ";", false));
        }

        if (beDepends != null) {
            for (String beDepend : beDepends) {
                synchronized (beDependOnJobMap) {
                    Set<String> beContainedJobs = beDependOnJobMap.get(beDepend);
                    if (beContainedJobs == null) {
                        beContainedJobs = new LinkedHashSet<String>(8);
                        beDependOnJobMap.put(beDepend, beContainedJobs);
                    }
                    beContainedJobs.add(job);
                }
            }
        }
    }
}
