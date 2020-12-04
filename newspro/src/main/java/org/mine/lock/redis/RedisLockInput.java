package org.mine.lock.redis;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: RedisLockInput
 * @date 2020/11/2711:27
 */
public class RedisLockInput {
    /**
     * 业务逻辑描述
     */
    private String logicDesc;
    /**
     * key值
     */
    private String key;
    /**
     * value值
     */
    private Object value;
    /**
     * 过期时间
     */
    private Long expire;
    /**
     * 重试次数
     */
    private Long retryTimes;

    public RedisLockInput() {
        this.logicDesc = "";
        this.key = "";
        this.value = "";
        this.expire = 0L;
        this.retryTimes = 0L;
    }

    /**
     * 业务逻辑描述
     * @return the logicDesc as logicDesc
     */
    public String getLogicDesc() {
        return logicDesc;
    }

    /**
     * 业务逻辑描述
     * @param logicDesc the logicDesc to set
     */
    public RedisLockInput setLogicDesc(String logicDesc) {
        this.logicDesc = logicDesc;
        return this;
    }

    /**
     * key值
     * @return the key as key
     */
    public String getKey() {
        return key;
    }

    /**
     * key值
     * @param key the key to set
     */
    public RedisLockInput setKey(String key) {
        this.key = key;
        return this;
    }

    /**
     * value值
     * @return the value as value
     */
    public Object getValue() {
        return value;
    }

    /**
     * value值
     * @param value the value to set
     */
    public RedisLockInput setValue(Object value) {
        this.value = value;
        return this;
    }

    /**
     * 过期时间
     * @return the expire as expire
     */
    public Long getExpire() {
        return expire;
    }

    /**
     * 过期时间
     * @param expire the expire to set
     */
    public RedisLockInput setExpire(Long expire) {
        this.expire = expire;
        return this;
    }

    /**
     * 重试次数
     * @return the retryTimes as retryTimes
     */
    public Long getRetryTimes() {
        return retryTimes;
    }

    /**
     * 重试次数
     * @param retryTimes the retryTimes to set
     */
    public RedisLockInput setRetryTimes(Long retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    @Override
    public String toString() {
        return "RedisLockInput{" + "logicDesc='" + logicDesc + '\'' + ", key='" + key + '\'' +
                ", value=" + value + ", expire=" + expire + ", retryTimes=" + retryTimes + '}';
    }
}
