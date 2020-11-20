package org.mine.aplt.Cache;

import org.mine.aplt.util.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *   此缓存是为了服务于JOB执行失败时, 将上个STEP传输的Map值存储到内存中，待重启时直接获取，以保证JOB的业务正确性和连贯性。</br>
 *  当前缓存在服务重启之后会消失，需要借助于第三方来管理，后期将其维护成存置redis中。
 * </p>
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: StepReturnMapCache
 * @date 2020/9/2419:32
 */
public class StepReturnMapCache {
    /**
     * 存储上一个STEP实例返回值
     */
    private static Map<String, Map<String, Object>> stepReturnMap = new ConcurrentHashMap<>(64);
    /**
     * 存储JOB实例中已经成功的STEP实例.
     */
    private static Map<String, Map<String, Object>> stepSuccessMap = new ConcurrentHashMap<>(64);

    /**
     * @return the stepReturnMap as stepReturnMap
     */
    public static Map<String, Map<String, Object>> getStepReturnMap() {
        return stepReturnMap;
    }

    /**
     * @return the stepSuccessMap as stepSuccessMap
     */
    public static Map<String, Map<String, Object>> getStepSuccessMap() {
        return stepSuccessMap;
    }

    /**
    * 获取返回值
    * @param key STEP实例
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: wntl
    * @Date: 2020/9/27
    */
    public static Map<String, Object> get(String key) {
        if (key == null) {
            return null;
        }
        return getStepReturnMap().getOrDefault(key, null);
    }
    /**
    * 获取已成功的STEP实例数据
    * @param key JOB实例
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    * @Author: wntl
    * @Date: 2020/9/27
    */
    public static Map<String, Object> getSuccessStep(String key) {
        if (key == null) {
            return null;
        }
        return getStepSuccessMap().getOrDefault(key, null);
    }
    /**
    * 插入返回数据
    * @param key STEP实例
    * @param value
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/27
    */
    public static void put(String key, Map<String, Object> value) {
        if (key == null || CommonUtils.isEmpty(value)) {
            return;
        }
        getStepReturnMap().put(key, value);
    }
    /**
    * 插入已成功的STEP实例
    * @param key JOB实例
    * @param secKey
    * @return: void
    * @Author: wntl
    * @Date: 2020/9/27
    */
    public static void putSuccessStep(String key, String secKey) {
        if (CommonUtils.isEmpty(key) || CommonUtils.isEmpty(secKey)) {
            return;
        }
//        stepReturnMap.computeIfAbsent(key, k -> new HashMap<>(64));

        if (getStepSuccessMap().get(key) == null) {
            getStepSuccessMap().put(key, new HashMap<>(64));
        }
        getStepSuccessMap().get(key).put(secKey, secKey);
    }
    /**
    * 清除STEP实例的返回值
    * @param key STEP实例
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/9/27
    */
    public static boolean remove(String key) {
        if (key == null) {
            return false;
        }

        if (!getStepReturnMap().containsKey(key)) {
            return false;
        }
        getStepReturnMap().remove(key);
        return true;
    }
    /**
    * 清除成功的STEP实例
    * @param key JOB实例
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/9/27
    */
    public static boolean removeSuccessStep(String key) {
        if (key == null) {
            return false;
        }

        if (!getStepSuccessMap().containsKey(key)) {
            return false;
        }
        getStepSuccessMap().remove(key);
        return true;
    }
}
