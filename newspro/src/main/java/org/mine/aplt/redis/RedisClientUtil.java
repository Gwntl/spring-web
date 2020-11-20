package org.mine.aplt.redis;

import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.mine.aplt.redis.CustomRedisTemplate.ZSetOperatorInput;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.redis.connection.RedisListCommands.Position;

/**
* redis客户端基础命令操作类
* @filename: RedisClient
* @author wntl
* @date 2020/11/18 19:57
* @version v1.0
*/
@Component
public class RedisClientUtil {
    final static Logger log = LoggerFactory.getLogger(RedisClientUtil.class);
    @Autowired
    private RedisTemplate<String, Object> template;
    @Autowired
    private CustomRedisTemplate customRedisTemplate;

    /**
    * 根据条件获取redis中对应的键列表
    * @param pattern
    * @return: java.util.Set<java.lang.String>
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Set<String> keys(String pattern) {
        try {
            if (CommonUtils.isEmpty(pattern)) {
                return null;
            }
            return template.keys(pattern);
        } catch (Exception e) {
            log.error("redis existsKey error : ", e);
            return null;
        }
    }

    /**
    * 判断key是否存在. exists key.
    * @param key
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Boolean existsKey(String key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return false;
            }
            return template.hasKey(key);
        } catch (Exception e) {
            log.error("redis existsKey error : ", e);
            return false;
        }
    }

    /**
    * 删除指定key
    * @param key
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public boolean delKey(String key) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return false;
            }
            template.delete(key);
            return true;
        } catch (Exception e) {
            log.error("redis existsKey error : ", e);
            return false;
        }
    }
    /**
    * 批量删除指定key
    * @param key
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public boolean delKeys(List<String> key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return false;
            }
            template.delete(key);
            return true;
        } catch (Exception e) {
            log.error("redis existsKey error : ", e);
            return false;
        }
    }

    /**
    * 选择数据库(默认16个数据库，在配置中可更改. 下标从0-15)
    * @param index
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Boolean selectDB(int index) {
        try {
            if (index < 0) {
                return false;
            }
            return customRedisTemplate.selectDB(index);
        } catch (Exception e) {
            log.error("redis selectDB error : ", e);
            return false;
        }
    }
    /**
    * 获取当前数据库中键的数量
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Long dbSize() {
        try {
            return customRedisTemplate.dbSize();
        } catch (Exception e) {
            log.error("redis selectDB error : ", e);
            return -1L;
        }
    }

    /**
    * 获取key类型
    * @param key
    * @return: org.springframework.data.redis.connection.DataType
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public String redisType(String key) {
        try {
            if (!existsKey(key)) {
                return "";
            }
            return template.type(key).code();
        } catch (Exception e) {
            log.error("redis selectDB error : ", e);
            return "";
        }
    }

    /**
    * 设置键过期时间. 以秒为单位.
    * @param key
    * @param time
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public Boolean expire(String key, Long time){
        try {
            return template.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("redis expire error : ", e);
            return false;
        }
    }
    /**
    * 设置键过期时间,时间戳模式.
    * @param key
    * @param date
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public Boolean expireAt(String key, Date date) {
        try {
            return template.expireAt(key, date);
        } catch (Exception e) {
            log.error("redis expireAt error : ", e);
            return false;
        }
    }

    /**
    * 删除键过期时间，使键持久化.
    * @param key
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public Boolean persist(String key) {
        try {
            return template.persist(key);
        } catch (Exception e) {
            log.error("redis persist error : ", e);
            return false;
        }
    }

    /**
    * 获取键剩余到期时间.以秒为单位.
    * @param key
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public Long ttl(String key) {
        try {
            return template.getExpire(key);
        } catch (Exception e) {
            log.error("redis ttl error : ", e);
            return -1L;
        }
    }

    /**
    * 随机返回一个键
    * @return: java.lang.String
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public String randomKey() {
        try {
            return template.randomKey();
        } catch (Exception e) {
            log.error("redis randomKey error : ", e);
            return null;
        }
    }

    /**
    * 重命名键
    * @param oldKey
    * @param newKey
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public Boolean rename(String oldKey, String newKey) {
        try {
            template.rename(oldKey, newKey);
            return true;
        } catch (Exception e) {
            log.error("redis persist error : ", e);
            return false;
        }
    }

    /**
    * 当新键不存在时则重命名
    * @param oldKey
    * @param newKey
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/19
    */
    public Boolean renameNx(String oldKey, String newKey){
        try {
            return template.renameIfAbsent(oldKey, newKey);
        } catch (Exception e) {
            log.error("redis persist error : ", e);
            return false;
        }
    }

    /* ====================以下为Redis字符串操作======================== */
    /**
    * 值插入(String类型). 调用set key value
    * @param key
    * @param value
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/5
    */
    public boolean set(String key, Object value) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return false;
            }
            template.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis set error : ", e);
            return false;
        }
    }

    /**
    * 设置值并且指定超时时间, 单位为秒/毫秒.<br/>
    * 最终调用setEX key time value(set key value ex time)/pSetEX key time value(set key value px time)
    * @param key
    * @param value
    * @param time
    * @param unit
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/5
    */
    public boolean set(String key, Object value, Long time, TimeUnit unit) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return false;
            }
            if (time > 0) {
                template.opsForValue().set(key, value, time, unit);
                return true;
            } else {
                log.debug("未指定超时时间.");
                return false;
            }
        } catch (Exception e) {
            log.error("redis set key with time(TimeUnit) error : ", e);
            return false;
        }
    }

    /**
    * 批量设置key-value值. mSet key value [key value...]
    * @param data
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/5
    */
    public boolean multiSet(Map<String, Object> data) {
        try {
            if (CommonUtils.isEmpty(data)) {
                log.debug("multiSet key is null");
                return false;
            }
            template.opsForValue().multiSet(data);
            return true;
        } catch (Exception e) {
            log.error("multiSet error : ", e);
            return false;
        }
    }

    /**
    * 获取多个key的value值. mGet key [key...]
    * @param keys
    * @return: java.util.List<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/5
    */
    public List<Object> multiGet(List<String> keys) {
        try {
            if (CommonUtils.isEmpty(keys)) {
                return null;
            }
            return template.opsForValue().multiGet(keys);
        } catch (Exception e) {
            log.error("multiGet error : ", e);
            return null;
        }
    }

    /**
     * 根据key获取值(String类型)。 get key
     * @param key
     * @return: java.lang.Object
     * @Author: wntl
     * @Date: 2020/11/5
     */
    public Object get(String key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForValue().get(key);
        } catch (Exception e) {
            log.error("get error : ", e);
            return null;
        }
    }

    /**
    * 当且仅当key中不存在值时才设置值. set key value NX/setNX key value.
    * @param key
    * @param value
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/5
    */
    public boolean setIfAbsent(String key, Object value) {
        try {
            if (CommonUtils.isEmpty(key)) {
                log.debug("setIfAbsent key is null");
                return false;
            }
            return template.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            log.error("setIfAbsent error : ", e);
            return false;
        }
    }
    /**
    * 设置多个不存在value的key值. mSetNX key value [key value...]
    * @param data
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/5
    */
    public boolean multiSetIfAbsent(Map<String, Object> data) {
        try {
            if (CommonUtils.isEmpty(data)) {
                log.debug("multiSetIfAbsent data is null");
                return false;
            }
            return template.opsForValue().multiSetIfAbsent(data);
        } catch (Exception e) {
            log.error("multiSetIfAbsent error : ", e);
            return false;
        }
    }

    /**
     * 截取字符串, 根据传入的起始位置和最终位置截取字符串并返回. getRange key start end.
     * @param key
     * @param start
     * @param end
     * @return: java.lang.String
     * @Author: wntl
     * @Date: 2020/11/5
     */
    public String getRange(String key, Long start, Long end) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return "";
            }
            return template.opsForValue().get(key, start, end);
        } catch (Exception e) {
            log.error("getRange error : ", e);
            return "";
        }
    }

    /**
    * 设置新值并返回旧值. getSet key value.
    * @param key
    * @param value
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/5
    */
    public Object getAndSet(String key, Object value){
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForValue().getAndSet(key, value);
        } catch (Exception e) {
            log.error("getAndSet error : ", e);
            return null;
        }
    }

    /**
     * 截取字符串. 将指定位置之后的字符串替换为指定的字符串.<br/>
     * 调用setRange key offset value命令.
     * @param key
     * @param value
     * @param offset
     * @return: boolean
     * @Author: wntl
     * @Date: 2020/11/5
     */
    public boolean setRange(String key, Object value, Long offset) {
        try {
            if (CommonUtils.isEmpty(key)) {
                log.debug("setRange key is null");
                return false;
            }
            template.opsForValue().set(key, value, offset);
            return true;
        } catch (Exception e) {
            log.error("redis setRange error : ", e);
            return false;
        }
    }

    /**
    * 为key中的值追加字符值并返回追加后的字符长度. append key value.
    * @param key
    * @param value
    * @return: java.lang.Integer
    * @Author: wntl
    * @Date: 2020/11/5
    */
    public Integer append(String key, String value) {
        try {
            if (CommonUtils.isEmpty(key)) {
                log.debug("append key is null");
                return -1;
            }
            return template.opsForValue().append(key, value);
        } catch (Exception e) {
            log.error("redis append error : ", e);
            return -1;
        }
    }

    /**
    * 获取对应key中字符串值的长度
    * @param key
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/5
    */
    public Long size(String key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                log.debug("size key is null");
                return -1L;
            }
            return template.opsForValue().size(key);
        } catch (Exception e) {
            log.error("redis strLen error : ", e);
            return -1L;
        }
    }

    /**
    * 进行加1操作时，需要保证redis中的值为数值类型(当key不存在时会先初始化key的值为0，再执行操作). incr key.
    * @param key
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Long increment(String key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                log.debug("increment key is null");
                return -1L;
            }
            return customRedisTemplate.incr(key);
        } catch (Exception e) {
            log.error("redis increment error : ", e);
            return -1L;
        }
    }
    /**
    * 进行加法操作时，需要保证redis中的值为数值类型(当key不存在时会先初始化key的值为0，再执行操作).<br/>
    * incrBy key increment. increment(64位有符号数字)值可为负值，为负值时代表减法。
    * @param k
    * @param l
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Long incrementBy(String k, Long l) {
        try {
            if (CommonUtils.isEmpty(k)) {
                log.debug("incrementBy key is null");
                return -1L;
            }
            return template.opsForValue().increment(k, l);
        } catch (Exception e) {
            log.error("redis incrementBy error : ", e);
            return -1L;
        }
    }

    /**
    * 进行加法操作时，需要保证redis中的值为数值类型(当key不存在时会先初始化key的值为0，再执行操作).<br/>
    * incrByFloat key increment. increment(64位有符号数字)值可为负值，为负值时代表减法。
    * @param k
    * @param d
    * @return: java.lang.Double
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Double incrementBy(String k, Double d) {
        try {
            if (CommonUtils.isEmpty(k)) {
                log.debug("incrementBy key is null");
                return -1.00D;
            }
            return template.opsForValue().increment(k, d);
        } catch (Exception e) {
            log.error("redis incrementBy error : ", e);
            return -1.00D;
        }
    }

    /**
    * 进行减1操作时，需要保证redis中的值为数值类型(当key不存在时会先初始化key的值为0，再执行操作). decr key.
    * @param key
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Long decrement(String key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                log.debug("decrement key is null");
                return -1L;
            }
            return customRedisTemplate.decr(key);
        } catch (Exception e) {
            log.error("redis increment error : ", e);
            return -1L;
        }
    }

    /**
    * 进行减法操作时，需要保证redis中的值为数值类型(当key不存在时会先初始化key的值为0，再执行操作).<br/>
    * decrBy key decrement. increment(64位有符号数字)值可为负值，为负值时代表加法。
    * @param k
    * @param l
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Long decrementBy(String k, Long l) {
        try {
            if (CommonUtils.isEmpty(k)) {
                log.debug("decrementBy key is null");
                return -1L;
            }
            return customRedisTemplate.decrementBy(k, l);
        } catch (Exception e) {
            log.error("redis decrementBy error : ", e);
            return -1L;
        }
    }

    /**
    * 设置键的偏移量(字符串是动态的),返回当前位置原来存储的值. setBit key offset value(0/1).
    * @param key
    * @param offset
    * @param value
    * @return: java.lang.Boolean
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Integer setBit(String key, Long offset, Integer value) {
        try {
            if (CommonUtils.isEmpty(key)) {
                log.debug("setBit key is null");
                return -1;
            }
            return template.opsForValue().setBit(key, offset, value != 0) ? 1 : 0;
        } catch (Exception e) {
            log.error("redis setBit error : ", e);
            return -1;
        }
    }

    /**
    * 获取key指定位置的偏移量(当offset大于字符串长度时返回0).getBit key offset.
    * @param key
    * @param offset
    * @return: java.lang.Integer
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Integer getBit(String key, Long offset) {
        try {
            if (CommonUtils.isEmpty(key)) {
                log.debug("getBit key is null");
                return -1;
            }
            return template.opsForValue().getBit(key, offset) ? 1 : 0;
        } catch (Exception e) {
            log.error("redis getBit error : ", e);
            return -1;
        }
    }
    /* ====================以上为Redis字符串操作======================== */

    /* ====================以下为Redis哈希操作======================== */
    /**
    * 向Redis的哈希结构中塞值. hSet key field value.
    * @param key
    * @param field
    * @param value
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public boolean hashSet(String key, String field, Object value) {
        try {
            if (CommonUtils.isEmpty(key) || CommonUtils.isEmpty(field)) {
                return false;
            }
            template.opsForHash().put(key, field, value);
            return true;
        } catch (Exception e) {
            log.error("redis hashSet error : ", e);
            return false;
        }
    }

    /**
    * 为hash中不存在的域设置值(当field不存在时设置值). hSetNx key field value.
    * @param key
    * @param field
    * @param value
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public boolean hashSetIfAbsent(String key, String field, Object value) {
        try {
            return template.opsForHash().putIfAbsent(key, field, value);
        } catch (Exception e) {
            log.error("redis hashSetIfAbsent error : ", e);
            return false;
        }
    }

    /**
    * 为hash批量设置值.hMSet key field value [field value...].
    * @param key
    * @param fields
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public boolean hashMultiSet(String key, Map<String, Object> fields) {
        try {
            if (CommonUtils.isEmpty(key) || CommonUtils.isEmpty(fields)) {
                return false;
            }
            template.opsForHash().putAll(key, fields);
            return true;
        } catch (Exception e) {
            log.error("redis hashMultiSet error : ", e);
            return false;
        }
    }

    /**
    * 根据key和field获取值
    * @param key
    * @param field
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Object hashGet(String key, String field) {
        try {
            if (CommonUtils.isEmpty(key) || CommonUtils.isEmpty(field) || hExistsField(key, field)) {
                return null;
            }
            return template.opsForHash().get(key, field);
        } catch (Exception e) {
            log.error("redis hashGet error : ", e);
            return null;
        }
    }

    /**
    * 获取hash的key中所有的域和值. hGetAll key.
    * @param key
    * @return: java.util.Map<java.lang.Object,java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Map<Object, Object> hashGetAll(String key) {
        try {
            if (!existsKey(key)) {
                return null;
            }
            return template.opsForHash().entries(key);
        } catch (Exception e) {
            log.error("redis hashGetAll error : ", e);
            return null;
        }
    }

    /**
    * 批量获取hash中域值. hMGet key field [field...].
    * @param key
    * @param fields
    * @return: java.util.List<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public List<Object> hashMultiGet(String key, List<Object> fields) {
        try {
            if (CommonUtils.isEmpty(key) || CommonUtils.isEmpty(fields)) {
                return null;
            }
            return template.opsForHash().multiGet(key, fields);
        } catch (Exception e) {
            log.error("redis hashMultiGet error : ", e);
            return null;
        }
    }

    /**
    * 获取hash中指定的key中的所有域. hKeys key.
    * @param key
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Set<Object> hashKeys(String key) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return null;
            }
            return template.opsForHash().keys(key);
        } catch (Exception e) {
            log.error("redis hashKeys error : ", e);
            return null;
        }
    }

    /**
    * 获取hash中指定key的所有域对应的值. hValS key.
    * @param key
    * @return: java.util.List<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public List<Object> hashValues(String key) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return null;
            }
            return template.opsForHash().values(key);
        } catch (Exception e) {
            log.error("redis hashKeys error : ", e);
            return null;
        }
    }
    /**
    * 获取hash中指定key的域数量. hLen key.
    * @param key
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public Long hashLength(String key) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return -1L;
            }
            return template.opsForHash().size(key);
        } catch (Exception e) {
            log.error("redis hashKeys error : ", e);
            return -1L;
        }
    }

//    public Long hashFieldLength(String key, String field) {
//        return template.opsForHash().
//    }

    /**
    * 判断指定key和field在数据库中是否存在. hExists key field.
    * @param key
    * @param field
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/6
    */
    public boolean hExistsField(String key, String field) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                log.debug("hExistsField key is null");
                return false;
            }
            return template.opsForHash().hasKey(key, field);
        } catch (Exception e) {
            log.error("redis hExistsField error : ", e);
            return false;
        }
    }

    /**
    * Long型加减法操作. l为负值时代表减法. HIncrBy key field increment.
    * @param key
    * @param field
    * @param l
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long hIncrementLong(String key, String field, Long l) {
        try {
            if (CommonUtils.isEmpty(key) || !hExistsField(key, field)) {
                return -1L;
            }
            return template.opsForHash().increment(key, field, l);
        } catch (Exception e) {
            log.error("redis hIncrementLong error : ", e);
            return -1L;
        }
    }

    /**
    * Double型加减法操作. d为负值时代表减法. HIncrByFloat key field increment.
    * @param key
    * @param field
    * @param d
    * @return: java.lang.Double
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Double hIncrementFloat(String key, String field, Double d) {
        try {
            if (CommonUtils.isEmpty(key) || !hExistsField(key, field)) {
                return -1.00D;
            }
            return template.opsForHash().increment(key, field, d);
        } catch (Exception e) {
            log.error("redis hIncrementFloat error : ", e);
            return -1.00D;
        }
    }

    /***
    * 批量删除key中的域,返回删除域的数量(剔除不存在的field). HDel key field [field...]
    * @param key
    * @param fields
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long hDel(String key, Object... fields){
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                log.debug("hDel key is null");
                return -1L;
            }
            return template.opsForHash().delete(key, fields);
        } catch (Exception e) {
            log.error("redis hDel error : ", e);
            return -1L;
        }
    }
    /* ====================以上为Redis哈希操作======================== */

    /* ====================以下为List列表操作======================== */
    /**
    * 从左边批量插入数据,从左到右依次插入. LPush key value [value...]
    * @param key
    * @param values
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long leftPush(String key, Object... values) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForList().leftPushAll(key, values);
        } catch (Exception e) {
            log.error("redis leftPop error : ", e);
            return -1L;
        }
    }

    /**
     * 从左边批量插入数据, 从左到右依次插入. LPush key value [value...]
     * @param key
     * @param values
     * @return: java.lang.Long
     * @Author: wntl
     * @Date: 2020/11/13
     */
    public Long leftPushAll(String key, Object... values) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForList().leftPushAll(key, values);
        } catch (Exception e) {
            log.error("redis leftPop error : ", e);
            return -1L;
        }
    }

    /**
     * 从左边批量插入数据, 从左到右依次插入. LPush key value [value...]
     * @param key
     * @param values
     * @return: java.lang.Long
     * @Author: wntl
     * @Date: 2020/11/13
     */
    public Long leftPushAll(String key, Collection<Object> values) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForList().leftPushAll(key, values);
        } catch (Exception e) {
            log.error("redis leftPop error : ", e);
            return -1L;
        }
    }

    /**
    * 当key存在时, 将value值插入列表的头部; 不存在则什么都不做. 返回插入后列表的长度. LPushX key value.
    * @param key
    * @param value
    * @return: long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public long leftPushIfPresent(String key, Object value) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return -1L;
            }
            return template.opsForList().leftPushIfPresent(key, value);
        } catch (Exception e) {
            log.error("redis leftPop error : ", e);
            return -1L;
        }
    }

    /**
    * 从列表右边开始插入数据.最新的数据处于右边表头,返回插入后的列表长度. RPush key value.
    * @param key
    * @param value
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long rightPush(String key, Object value) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForList().rightPush(key, value);
        } catch (Exception e) {
            log.error("redis leftPop error : ", e);
            return -1L;
        }
    }

    /**
    * 从右边批量插入数据, 从左到右依次插入. RPush key value [value...]
    * @param key
    * @param values
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long rightPushAll(String key, Object... values) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForList().rightPushAll(key, values);
        } catch (Exception e) {
            log.error("redis leftPop error : ", e);
            return -1L;
        }
    }

    /**
     * 从右边批量插入数据, 从左到右依次插入. RPush key value [value...]
     * @param key
     * @param values
     * @return: java.lang.Long
     * @Author: wntl
     * @Date: 2020/11/13
     */
    public Long rightPushAll(String key, Collection<Object> values) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForList().rightPushAll(key, values);
        } catch (Exception e) {
            log.error("redis leftPop error : ", e);
            return -1L;
        }
    }


    /**
    * 将数据插入到列表的尾部. RPushX key value.
    * @param key
    * @param value
    * @return: long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public long rightPushIfPresent(String key, Object value) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return -1L;
            }
            return template.opsForList().rightPushIfPresent(key, value);
        } catch (Exception e) {
            log.error("redis leftPop error : ", e);
            return -1L;
        }
    }

    /**
    * 根据指定条件插入指定值. 返回插入后的列表长度.<br/>
    * 使用命令: LInsert key BEFORE|AFTER pivot value. 代表将value值插入key中的pivot值的前或后面.<br/>
    * 插入时若存在相同的pivot值, 插入的位置为左边第一个pivot值的前后.
    * <p>
    *   例: 原列表: list_1 : {'c', 'a', 'b', 'a', 'a'}.<br/>
    *   exec: pushByCondition(list_1, 'a', 'd', Position_BEFORE);<br/>
    *   result : list_1 : {'c', 'd', 'a', 'b', 'a', 'a'};<br/>
    *   exec : pushByCondition(list_1, 'a', 'r', Position_AFTER);<br/>
    *   result : list_1 : {'c', 'd', 'a', 'r', 'b', 'a', 'a'};<br/>
    * <p/>
    * @param key
    * @param pivot
    * @param pushValue
    * @param position 传入值可选: <code>{@link Position}</code> 中的BEFORE/AFTER
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long pushByPosition(String key, Object pivot, Object pushValue, Position position){
        try {
            //当key不存在或者pivot值不存在时，返回失败.
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            switch (position) {
                case BEFORE:
                    return template.opsForList().leftPush(key, pivot, pushValue);
                case AFTER:
                    return template.opsForList().rightPush(key, pivot, pushValue);
                default:
                   throw new NoSuchMethodException("传入标志错误");
            }
        } catch (Exception e) {
            log.error("redis pushByPosition error : ", e);
            return -1L;
        }
    }

    /**
    * 重新设置值. 将列表中指定位置的值设置为指定值. LSet key index value.
    * @param key
    * @param index
    * @param value
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public boolean listSet(String key, int index, Object value) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return false;
            }
            template.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("redis listSet error : ", e);
            return false;
        }
    }

    /**
    * 获取列表中的元素个数. LLen key.
    * @param key
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long listSize(String key) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return -1L;
            }
            return template.opsForList().size(key);
        } catch (Exception e) {
            log.error("redis listSize error : ", e);
            return -1L;
        }
    }

    /**
    * 获取列表中指定位置的值. LIndex key index. index 可为负值, -1为倒数第一个, -2为倒数第二个...
    * @param key
    * @param index
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Object getValueOnIndex(String key, int index) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return null;
            }
            return customRedisTemplate.getValueOnIndex(key, index);
        } catch (Exception e) {
            log.error("redis getValueOnIndex error : ", e);
            return null;
        }
    }

    /**
    * 获取列表指定区间内的元素. LRange key start end. start和end均可为负值.
    * @param key
    * @param start
    * @param end
    * @return: java.util.List<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public List<Object> range(String key, Long start, Long end) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return null;
            }
            return template.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("redis range error : ", e);
            return null;
        }
    }

    /**
     * 返回并删除列表的头元素. LPop key.
     * @param key
     * @return: java.lang.Long
     * @Author: wntl
     * @Date: 2020/11/13
     */
    public Object leftPop(String key) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return null;
            }
            return template.opsForList().leftPop(key);
        } catch (Exception e) {
            log.error("redis leftPop error : ", e);
            return null;
        }
    }

    /**
    * 阻塞式获取队首元素并删除. BLPop key [key...] timeout. 当前使用为当个key阻塞.
    * @param key
    * @param timeOut
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Object leftPopInBlocking(String key, Long timeOut, TimeUnit unit) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return null;
            }
            return template.opsForList().leftPop(key, timeOut, unit);
        } catch (Exception e) {
            log.error("redis leftPopInBlocking error : ", e);
            return null;
        }
    }

    /**
    * 返回并删除列表的尾元素. RPop key.
    * @param key
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Object rightPop(String key) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return null;
            }
            return template.opsForList().rightPop(key);
        } catch (Exception e) {
            log.error("redis rightPop error : ", e);
            return null;
        }
    }

    /**
     * 阻塞式获取队尾元素并删除. BRPop key [key...] timeout. 当前使用为当个key阻塞.
     * @param key
     * @param timeOut
     * @return: java.lang.Object
     * @Author: wntl
     * @Date: 2020/11/13
     */
    public Object rightPopInBlocking(String key, Long timeOut, TimeUnit unit) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return null;
            }
            return template.opsForList().rightPop(key, timeOut, unit);
        } catch (Exception e) {
            log.error("redis rightPopInBlocking error : ", e);
            return null;
        }
    }

    /**
    * 删除所有与value值相等的元素.
    * @param key
    * @param value
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long removeAll(String key, Object value) {
        return removeElement(key, 0L, value);
    }

    /**
    * 从列表表头开始删除count个与value值相等的元素.
    * @param key
    * @param count
    * @param value
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long removeFromLeft(String key, Long count, Object value) {
        return removeElement(key, Math.abs(count), value);
    }

    /**
    * 从列表表尾开始删除count个与value值相等的元素.
    * @param key
    * @param count
    * @param value
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long removeFromRight(String key, Long count, Object value) {
        return removeElement(key, Math.abs(count) * -1L, value);
    }

    /**
    * 删除元素. 根据count的值，删除列表key中与指定value值相等的的元素. LRem key count value.
    * <p>
    *   1、当count=0时, 表示删除所有与value值相等的元素。
    *   2、当count>0时，表示从表头开始向表尾搜索，删除与value值相等的元素，个数为count个。
    *   3、当count<0时，表示从表尾向表头搜索，删除与value值相等的元素，个数为count的绝对值个。
    * <p/>
    * @param key
    * @param count
    * @param value
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Long removeElement(String key, Long count, Object value) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("redis removeElement error : ", e);
            return -1L;
        }
    }

    /**
    * 截取列表中的元素. 根据start和end来截取. LTrim key start end
    * @param key
    * @param start
    * @param end
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public boolean trimElement(String key, Long start, Long end) {
        try {
            if (CommonUtils.isEmpty(key) || !existsKey(key)) {
                return false;
            }
            template.opsForList().trim(key, start, end);
            return true;
        } catch (Exception e) {
            log.error("redis trimElement error : ", e);
            return false;
        }
    }

    /**
    * 从sourceKey列表的表尾弹出元素，插入到destinationKey列表的表头. LPopRPush source destination.<br/>
    * 当sourceKey不存在时,返回nil并且不会执行下一步操纵. 如果source与destination相同，则相当于把列表的表尾元素插入到表头.
    * @param sourceKey
    * @param destinationKey
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Object leftPopAndRightPush(String sourceKey, String destinationKey) {
        try {
            if (CommonUtils.isEmpty(sourceKey) || CommonUtils.isEmpty(destinationKey) || !existsKey(sourceKey)) {
                return null;
            }
            return template.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
        } catch (Exception e) {
            log.error("redis leftPopAndRightPush error : ", e);
            return null;
        }
    }

    /**
    * 从sourceKey列表的表尾弹出元素，插入到destinationKey列表的表头的阻塞版本. BRPopLPush source destination timeout.
    * @param sourceKey
    * @param destinationKey
    * @param timeOut
    * @param unit
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/13
    */
    public Object leftPopAndRightPushInBlocking(String sourceKey, String destinationKey, Long timeOut, TimeUnit unit) {
        try {
            if (CommonUtils.isEmpty(sourceKey) || CommonUtils.isEmpty(destinationKey) || !existsKey(sourceKey)) {
                return null;
            }
            return template.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeOut, unit);
        } catch (Exception e) {
            log.error("redis leftPopAndRightPushInBlocking error : ", e);
            return null;
        }
    }
    /* ====================以上为List列表操作======================== */

    /* ====================以下为Set集合操作======================== */
    /**
    * 向Set集合中塞入值 sAdd key member [member...]. 返回成功插入的数量
    * @param key
    * @param member
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Long add_Set(String key, Object... member) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForSet().add(key, member);
        } catch (Exception e) {
            log.error("redis add_Set error : ", e);
            return -1L;
        }
    }

    /**
    * 向Set集合中塞值 sAdd key member [member...]
    * @param key
    * @param member
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Long add_Set(String key, Collection<?> member) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForSet().add(key, member);
        } catch (Exception e) {
            log.error("redis add_Set error : ", e);
            return -1L;
        }
    }

    /**
    * 将sourceKey中的元素member转移到destinationKey里，此操作是原子性操作.
    * sMove sourceKey destinationKey member. 当sourceKey不存在或者sourceKey不存在member时，什么也不会做。
    * @param sourceKey
    * @param destinationKey
    * @param member
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public boolean move_Set(String sourceKey, String destinationKey, Object member) {
        try {
            if (CommonUtils.isEmpty(sourceKey) || CommonUtils.isEmpty(destinationKey)) {
                return false;
            }
            return template.opsForSet().move(sourceKey, member, destinationKey);
        } catch (Exception e) {
            log.error("redis move_Set error : ", e);
            return false;
        }
    }

    /**
    * 判断元素是否在指定key的集合中.
    * sIsMember key member
    * @param key
    * @param member
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public boolean isMember_Set(String key, Object member){
        try {
            if (CommonUtils.isEmpty(key)) {
                return false;
            }
            return template.opsForSet().isMember(key, member);
        } catch (Exception e) {
            log.error("redis isMember_Set error : ", e);
            return false;
        }
    }

    /**
    * 获取集合中元素数量 SCard key.
    * @param key
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Long size_Set(String key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForSet().size(key);
        } catch (Exception e) {
            log.error("redis size_Set error : ", e);
            return -1L;
        }
    }

    /**
    * 获取集合中的所有元素. SMembers key.
    * @param key
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Set<Object> members_Set(String key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForSet().members(key);
        } catch (Exception e) {
            log.error("redis members_Set error : ", e);
            return null;
        }
    }

    /**
    * 从集合中随机获取一个元素. sRandMember
    * @param key
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Object randomMember_Set(String key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForSet().randomMember(key);
        } catch (Exception e) {
            log.error("redis randomMember_Set error : ", e);
            return null;
        }
    }

    /**
    * 从集合钟随机获取count个元素. sRandMember key count.<br/>
    * 当count>0时,返回count个元素, 元素各不相同.<br/>
    * 当count<0时,返回count个元素, 元素可能相同.<br/>
    * @param key
    * @param count
    * @return: java.util.List<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public List<Object> randomMembers_Set(String key, Long count) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForSet().randomMembers(key, count);
        } catch (Exception e) {
            log.error("redis randomMembers_Set error : ", e);
            return null;
        }
    }

    /**
    * 获取指定多个集合的并集. sUnion key [key...]
    * @param key
    * @param otherKey
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Set<Object> union_Set(String key, String otherKey) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForSet().union(key, otherKey);
        } catch (Exception e) {
            log.error("redis union_Set error : ", e);
            return null;
        }
    }

    /**
    * 获取指定多个集合的并集. sUnion key [key...]
    * @param key
    * @param otherKeys
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Set<Object> union_Set(String key, Collection<String> otherKeys) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForSet().union(key, otherKeys);
        } catch (Exception e) {
            log.error("redis union_Set error : ", e);
            return null;
        }
    }

    /**
     * 将指定key的元素并集保存至指定的key中并返回新key中的元素数量.
     * sUnionStore destinationKey key [key...]
     * @param destinationKey
     * @param key
     * @param otherKey
     * @return: java.lang.Long
     * @Author: wntl
     * @Date: 2020/11/17
     */
    public Long unionAndStore_Set(String destinationKey, String key,  String otherKey) {
        try {
            return template.opsForSet().unionAndStore(key, otherKey, destinationKey);
        } catch (Exception e) {
            log.error("redis unionAndStore_Set error : ", e);
            return -1L;
        }
    }

    /**
     * 将指定key中的元素并集保存至指定的key中并返回新key中的元素数量。
     * sUnionStore destinationKey key [key...].
     * @param destinationKey
     * @param key
     * @param otherKeys
     * @return: java.lang.Long
     * @Author: wntl
     * @Date: 2020/11/17
     */
    public Long unionAndStore_Set(String destinationKey, String key,  Collection<String> otherKeys) {
        try {
            return template.opsForSet().unionAndStore(key, otherKeys, destinationKey);
        } catch (Exception e) {
            log.error("redis unionAndStore_Set error : ", e);
            return -1L;
        }
    }

    /**
    * 获取指定多个集合的差集. SDiff key [key...]
    * @param key
    * @param otherKey
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Set<Object> difference_Set(String key, String otherKey) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForSet().difference(key, otherKey);
        } catch (Exception e) {
            log.error("redis difference_Set error : ", e);
            return null;
        }
    }

    /**
    * 获取指定多个集合的差集. 差集以第一个集合为主去除后续集合重复的数据,因此会出现key顺序不同时结果不一致. SDiff key [key...]
    * @param key
    * @param otherKeys
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Set<Object> difference_Set(String key, Collection<String> otherKeys) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForSet().difference(key, otherKeys);
        } catch (Exception e) {
            log.error("redis difference_Set error : ", e);
            return null;
        }
    }

    /**
    * 将多个集合的差集塞入指定的集合钟并且返回新集合的个数. SDiffStore destination key [key...]
    * @param key
    * @param otherKey
    * @param destinationKey
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Long differenceAndStore_Set(String key, String otherKey, String destinationKey) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForSet().differenceAndStore(key, otherKey, destinationKey);
        } catch (Exception e) {
            log.error("redis differenceAndStore_Set error : ", e);
            return -1L;
        }
    }

    /**
    * 将多个集合的差集塞入指定的集合中并且返回新集合的个数. SDiffStore destination key [key...]
    * @param key
    * @param otherKeys
    * @param destinationKey
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Long differenceAndStore_Set(String key, Collection<String> otherKeys, String destinationKey) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForSet().differenceAndStore(key, otherKeys, destinationKey);
        } catch (Exception e) {
            log.error("redis differenceAndStore_Set error : ", e);
            return -1L;
        }
    }


    /**
    * 获取多个集合的交集并返回. SInter key [key...]
    * @param key
    * @param otherKey
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Set<Object> interSect_Set(String key, String otherKey) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForSet().intersect(key, otherKey);
        } catch (Exception e) {
            log.error("redis interSect_set error : ", e);
            return null;
        }
    }

    /**
    * 获取多个集合的交易并返回. SInter key [key...]
    * @param key
    * @param otherKeys
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Set<Object> interSect_Set(String key, Collection<String> otherKeys) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForSet().intersect(key, otherKeys);
        } catch (Exception e) {
            log.error("redis interSect_set error : ", e);
            return null;
        }
    }

    /**
    * 获取多个集合的交集并且放入新集合中且返回新集合的元素数量. SInterStore key [key...]
    * @param key
    * @param otherKey
    * @param destinationKey
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Long intersectAndStore_Set(String key, String otherKey, String destinationKey){
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForSet().intersectAndStore(key, otherKey, destinationKey);
        } catch (Exception e) {
            log.error("redis intersectAndStore_set error : ", e);
            return -1L;
        }
    }

    /**
    * 获取多个集合的交集并且放入新集合中且返回新集合的元素数量. SInterStore key [key...]
    * @param key
    * @param otherKeys
    * @param destinationKey
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Long intersectAndStore_Set(String key, Collection<String> otherKeys, String destinationKey){
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForSet().intersectAndStore(key, otherKeys, destinationKey);
        } catch (Exception e) {
            log.error("redis intersectAndStore_set error : ", e);
            return -1L;
        }
    }

    /**
    * 随机删除集合中的一个元素并返回该值。 SPop key [count].
    * @param key
    * @return: java.lang.Object
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Object pop_Set(String key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForSet().pop(key);
        } catch (Exception e) {
            log.error("redis pop_set error : ", e);
            return null;
        }
    }

    /**
    * 删除集合中指定的元素并返回删除成功的个数. SRem key member [member...]
    * @param key
    * @param members
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Long remove_Set(String key, Object... members) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForSet().remove(key, members);
        } catch (Exception e) {
            log.error("redis remove_set error : ", e);
            return -1L;
        }
    }

    /* ====================以上为Set集合操作======================== */

    /* ====================以下为ZSet有序集合操作======================== */
    /**
    * 向有序集合中添加元素. ZAdd key score member.
    * @param key
    * @param value
    * @param score
    * @return: boolean
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public boolean zAdd(String key, Object value, Double score) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return false;
            }
            return template.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            log.error("redis zAdd error : ", e);
            return false;
        }
    }

    /**
    * 批量向有序集合中添加元素. 使用<code>{@link DefaultTypedTuple}</code>对象来保存score和member. 返回插入成功的元素数量.
    * zAdd key score member [[score member]...].  如果存在了member元素则更新对应的score值并且重新插入以便元素位置正确.
    * @param key
    * @param tuples
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/17
    */
    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().add(key, tuples);
        } catch (Exception e) {
            log.error("redis zAdd error : ", e);
            return -1L;
        }
    }

    /**
    * 有序集合中指定元素的score值增加(increment为正时)/减少(increment为负时). 当key不存在该命令相当于zAdd.
    * ZIncrBy key increment member. 返回member的score值.
    * @param key
    * @param member
    * @param increment
    * @return: java.lang.Double
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Double zIncrementScore(String key, Object member, Double increment) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1D;
            }
            return template.opsForZSet().incrementScore(key, member, increment);
        } catch (Exception e) {
            log.error("redis zIncrementScore error : ", e);
            return -1D;
        }
    }

    /**
    * 获取有序集合中的元素数量. ZCard key.
    * @param key
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zSize(String key) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().size(key);
        } catch (Exception e) {
            log.error("redis zSize error : ", e);
            return -1L;
        }
    }

    /**
    * 获取有序集合中指定分数范围内元素数量(默认含头含尾). ZCount key min max.
    * @param key
    * @param min
    * @param max
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zCount(String key, Double min, Double max) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().count(key, min, max);
        } catch (Exception e) {
            log.error("redis zCount error : ", e);
            return -1L;
        }
    }

    /**
    * 获取有序集合指定区间的元素(升序). ZRange key start end.
    * @param key
    * @param start
    * @param end
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<Object> zRange(String key, Long start, Long end) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.error("redis zRange error : ", e);
            return null;
        }
    }

    /**
    * 获取有序集合指定区间内的元素(升序并显示元素对应的分数). ZRange key start end WithSCORES.
    * @param key
    * @param start
    * @param end
    * @return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, Long start, Long end) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().rangeWithScores(key, start, end);
        } catch (Exception e) {
            log.error("redis zRangeWithScores error : ", e);
            return null;
        }
    }

    /**
    * 获取有序集合指定区间内的元素(降序). ZRevRange key start end.
    * @param key
    * @param start
    * @param end
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<Object> zRevRange(String key, Long start, Long end) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            log.error("redis zRevRange error : ", e);
            return null;
        }
    }

    /**
    * 获取有序集合指定区间内的元素(降序并返回元素对应的分数). ZRevRange key start end WithScores.
    * @param key
    * @param start
    * @param end
    * @return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<ZSetOperations.TypedTuple<Object>> zRevRangeWithScores(String key, Long start, Long end) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().reverseRangeWithScores(key, start, end);
        } catch (Exception e) {
            log.error("redis zRevRangeWithScores error : ", e);
            return null;
        }
    }

    /**
    * 获取有序集合内指定元素的分数. ZScore key member.
    * @param key
    * @param member
    * @return: java.lang.Double
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Double zScore(String key, Object member) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1D;
            }
            return template.opsForZSet().score(key, member);
        } catch (Exception e) {
            log.error("redis zScore error : ", e);
            return -1D;
        }
    }

    /**
    * 获取有序集合指定分数范围内的元素. 当前有序集合中的分数值都是相同的,他们按照字典顺序排序.如果有序集合中的元素分数值不相同，返回结果未知.
    * ZRangeByLev key min max.
    * @param key
    * @param min 当为null时, 表示使用特殊符号"-", 代表负无穷。 与max的特殊符号"+"(表示正无穷)结合使用时，返回有序集合中的所有元素.
    * @param max 当为null时，表示使用特殊符号"+"，代表正无穷，与min的特殊符号"-"(表示负无穷)结合使用时，返回有序集合中的所有元素.
    * @param isIncluding true时表示为闭区间[start, end]; false时表示为开区间(start, end).
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<Object> zRangeByLex(String key, Double min, Double max, boolean isIncluding) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().rangeByLex(key, isIncluding ? RedisZSetCommands.Range.range().gte(min).lte(max)
                    : RedisZSetCommands.Range.range().gt(min).lt(max));
        } catch (Exception e) {
            log.error("redis zRangeByLex error : ", e);
            return null;
        }
    }

    /**
    * 获取有序集合指定分数范围内的元素(分页). 当前有序集合中的分数值都是相同的,他们按照字典顺序排序.如果有序集合中的元素分数值不相同，返回结果未知.
    * ZRangeByLex key min max limit offset count
    * @param key
    * @param min 当为null时, 表示使用特殊符号"-", 代表负无穷。 与max的特殊符号"+"(表示正无穷)结合使用时，返回有序集合中的所有元素.
    * @param max 当为null时，表示使用特殊符号"+"，代表正无穷，与min的特殊符号"-"(表示负无穷)结合使用时，返回有序集合中的所有元素.
    * @param isIncluding true时表示为闭区间[start, end]; false时表示为开区间(start, end).
    * @param offset 分页使用, 代表从哪开始返回
    * @param count 分业使用，代表返回多少个元素
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<Object> zRangeByLex(String key, Double min, Double max, boolean isIncluding, int offset, int count) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().rangeByLex(key, isIncluding ? RedisZSetCommands.Range.range().gte(min).lte(max)
                    : RedisZSetCommands.Range.range().gt(min).lt(max), RedisZSetCommands.Limit.limit().offset(offset).count(count));
        } catch (Exception e) {
            log.error("redis zRangeByLex error : ", e);
            return null;
        }
    }

    /**
    * 获取有序区间内指定分数范围内的元素(默认是闭区间,从小到大). ZRangeByScore key min max.
    * @param key
    * @param min 当不知道最小值时可为Null, 会默认以“-inf”表示.
    * @param max 当不知道最大值时可为Null, 会默认以“+inf”表示.
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<Object> zRangeByScore(String key, Double min, Double max) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis zRangeByScore error : ", e);
            return null;
        }
    }

    /**
    * 获取有序区间内指定分数范围内的元素(分页)(默认是闭区间,从小到大). ZRangeByScore key min max limit offset count.
    * @param key
    * @param min 当不知道最小值时可为Null, 会默认以“-inf”表示.
    * @param max 当不知道最大值时可为Null, 会默认以“+inf”表示.
    * @param offset
    * @param count
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<Object> zRangeByScore(String key, Double min, Double max, Long offset, Long count) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().rangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("redis zRangeByScore error : ", e);
            return null;
        }
    }

    /**
    * 获取有序集合中指定分数区间的元素列表(开闭区间按operation来确定). 全闭区间使用上面方法.
    * @param key
    * @param min
    * @param max
    * @param offset 可为空/0. offset与count联合使用,形成分页效果.
    * @param count 可为空/0. offset与count联合使用,形成分页效果.
    * @param operation
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<Object> zRangeByScore(String key, Double min, Double max, Integer offset, Integer count,
            Integer operation) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return customRedisTemplate.rangeByScore(key, ZSetOperatorInput.getInput().setMin(min).setMax(max)
                    .setOffset(offset).setCount(count).setOperation(operation));
        } catch (Exception e) {
            log.error("redis zRangeByScore error : ", e);
            return null;
        }
    }

    /**
    * 获取有序集合中指定分数区间内的元素并返回分数(默认是闭区间,从小到大). ZRangeByScore key min max WithScores
    * @param key
    * @param min 当不知道最小值时可为Null, 会默认以“-inf”表示.
    * @param max 当不知道最大值时可为Null, 会默认以“+inf”表示.
    * @return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, Double min, Double max) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().rangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            log.error("redis zRangeByScoreWithScores error : ", e);
            return null;
        }
    }

    /**
    * 获取有序集合中指定分数区间内的元素并返回分数(分页)(默认是闭区间,从小到大). ZRangeByScore key min max WithScores limit offset count
    * @param key
    * @param min 当不知道最小值时可为Null, 会默认以“-inf”表示.
    * @param max 当不知道最大值时可为Null, 会默认以“+inf”表示.
    * @param offset
    * @param count
    * @return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, Double min, Double max,
            Long offset, Long count) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("redis zRangeByScoreWithScores error : ", e);
            return null;
        }
    }

    /**
    * 作用同zRangeByScore(...integer operation). 多返回元素对应的分数. 全闭区间使用上面方法.
    * @param key
    * @param min
    * @param max
    * @param offset
    * @param count
    * @param operation
    * @return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, Double min, Double max,
            Integer offset, Integer count, Integer operation) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return customRedisTemplate.rangeByScoreWithScores(key, ZSetOperatorInput.getInput().setMin(min).setMax(max)
                    .setOffset(offset).setCount(count).setOperation(operation));
        } catch (Exception e) {
            log.error("redis zRangeByScoreWithScores error : ", e);
            return null;
        }
    }


    /**
    * 获取有序区间内指定分数范围内的元素(默认是闭区间,从大到小). ZRevRangeByScore key min max.
    * @param key
    * @param min 当不知道最小值时可为Null, 会默认以“-inf”表示.
    * @param max 当不知道最大值时可为Null, 会默认以“+inf”表示.
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<Object> zRevRangeByScore(String key, Double min, Double max) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().reverseRangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis zRevRangeByScore error : ", e);
            return null;
        }
    }

    /**
    * 获取有序区间内指定分数范围内的元素(分页)(默认是闭区间,从大到小). ZRevRangeByScore key min max limit offset count.
    * @param key
    * @param min 当不知道最小值时可为Null, 会默认以“-inf”表示.
    * @param max 当不知道最大值时可为Null, 会默认以“+inf”表示.
    * @param offset
    * @param count
    * @return: java.util.Set<java.lang.Object>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<Object> zRevRangeByScore(String key, Double min, Double max, Long offset, Long count) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("redis zRevRangeByScore error : ", e);
            return null;
        }
    }

    public Set<Object> zRevRangeByScore(String key, Double min, Double max, Integer offset, Integer count, Integer operation) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return customRedisTemplate.reverseRangeByScore(key, ZSetOperatorInput.getInput().setMin(min).setMax(max)
                    .setOffset(offset).setCount(count).setOperation(operation));
        } catch (Exception e) {
            log.error("redis zRevRangeByScore error : ", e);
            return null;
        }
    }

    /**
    * 获取有序集合中指定分数区间内的元素并返回分数(默认是闭区间,从大到小). ZRevRangeByScore key min max WithScores
    * @param key
    * @param min 当不知道最小值时可为Null, 会默认以“-inf”表示.
    * @param max 当不知道最大值时可为Null, 会默认以“+inf”表示.
    * @return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<ZSetOperations.TypedTuple<Object>> zRevRangeByScoreWithScores(String key, Double min, Double max) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            log.error("redis zRevRangeByScoreWithScores error : ", e);
            return null;
        }
    }

    /**
    * 获取有序集合中指定分数区间内的元素并返回分数(分页)(默认是闭区间,从大到小). ZRevRangeByScore key min max WithScores limit offset count
    * @param key
    * @param min 当不知道最小值时可为Null, 会默认以“-inf”表示.
    * @param max 当不知道最大值时可为Null, 会默认以“+inf”表示.
    * @param offset
    * @param count
    * @return: java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Set<ZSetOperations.TypedTuple<Object>> zRevRangeByScoreWithScores(String key, Double min, Double max,
            Long offset, Long count) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return template.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("redis zRevRangeByScoreWithScores error : ", e);
            return null;
        }
    }

    public Set<ZSetOperations.TypedTuple<Object>> zRevRangeByScoreWithScores(String key, Double min, Double max,
            Integer offset, Integer count, Integer operation) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return null;
            }
            return customRedisTemplate.reverseRangeByScoreWithScores(key, ZSetOperatorInput.getInput().setMin(min).setMax(max)
                    .setOffset(offset).setCount(count).setOperation(operation));
        } catch (Exception e) {
            log.error("redis zRevRangeByScoreWithScores error : ", e);
            return null;
        }
    }

    /**
    * 获取元素在有序集合中的排名(从小到大,最小的排名为0). ZRank key member.
    * @param key
    * @param member
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zRank(String key, Object member) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().rank(key, member);
        } catch (Exception e) {
            log.error("redis zRank error : ", e);
            return -1L;
        }
    }

    /**
     * 获取元素在有序集合中的排名(倒序). ZRevRank key member.
     * @param key
     * @param member
     * @return
     */
    public Long zRevRank(String key, Object member) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().reverseRank(key, member);
        } catch (Exception e) {
            log.error("redis zRevRank error : ", e);
            return -1L;
        }
    }

    /**
    * 获取多个有序集合的交集并存储到指定有序集合中. 默认情况下: 交集中元素的score值为运算集合中的score之和. 返回保存到指定集合中的元素数量
    * ZInterStore destination numKeys(放在代码中实现不需要传递) key [key...]
    * @param key
    * @param otherKey
    * @param destinationKey
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zIntersectAndStore(String key, String otherKey, String destinationKey) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().intersectAndStore(key, otherKey, destinationKey);
        } catch (Exception e) {
            log.error("redis zIntersectAndStore error : ", e);
            return -1L;
        }
    }

    /**
    * 获取多个有序集合的交集并存储到指定有序集合中. 默认情况下: 交集中元素的score值为运算集合中的score之和. 返回保存到指定集合中的元素数量
    * ZInterStore destination numKeys(放在代码中实现不需要传递) key [key...]
    * @param key
    * @param otherKeys
    * @param destinationKey
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys, String destinationKey) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().intersectAndStore(key, otherKeys, destinationKey);
        } catch (Exception e) {
            log.error("redis zIntersectAndStore error : ", e);
            return -1L;
        }
    }

    /**
    * 获取指定多个有序集合的交集并插入指定的有序集合.返回保存到指定集合中的元素数量
    * ZInterStore destination numKeys key [key...] [WEIGHTS weight [weight...]] [AGGREGATE SUM|MAX|MIN]
    * WEIGHTS代表对应的有序集合有元素在交集中时score值(原score * weight).
    * @param key
    * @param keyWeight key对应的score乘数
    * @param destKey 目标有序集合key
    * @param keyInfos 存放参与运算的其他有序集合及对应的元素score的乘数
    * @param aggregate 指定交集中的score值获取方式. SUM->和, MAX->最大值, MIN->最小值.
    * 使用枚举类<code>{@link org.springframework.data.redis.connection.RedisZSetCommands.Aggregate}</code>来指定聚合方式
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zIntersectAndStore(String key, int keyWeight, String destKey, Map<String, Integer> keyInfos,
            final RedisZSetCommands.Aggregate aggregate) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            int size = keyInfos.size();
            List<String> otherKeys = new ArrayList<>(size);
            final int[] weights = new int[size];
            weights[0] = keyWeight;
            int count = 1;
            for (Map.Entry<String, Integer> entry : keyInfos.entrySet()) {
                otherKeys.add(entry.getKey());
                weights[count] = entry.getValue();
                count++;
            }
            return customRedisTemplate.intersectAndStore(key, otherKeys, destKey, weights, aggregate);
        } catch (Exception e) {
            log.error("redis zIntersectAndStore error : ", e);
            return -1L;
        }
    }

    /**
    * 获取多个有序集合中的并集并存入指定有序集合中.返回保存到指定集合中的元素数量。ZUnionStore destination numKeys key [key...]
    * @param key
    * @param otherKey
    * @param destinationKey
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zUnionAndStore(String key, String otherKey, String destinationKey) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().unionAndStore(key, otherKey, destinationKey);
        } catch (Exception e) {
            log.error("redis zUnionAndStore error : ", e);
            return -1L;
        }
    }

    /**
    * 获取多个有序集合中的并集并存入指定有序集合中.返回保存到指定集合中的元素数量. ZUnionStore destination numKeys key [key...]
    * @param key
    * @param otherKey
    * @param destinationKey
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zUnionAndStore(String key, Collection<String> otherKey, String destinationKey) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().unionAndStore(key, otherKey, destinationKey);
        } catch (Exception e) {
            log.error("redis zUnionAndStore error : ", e);
            return -1L;
        }
    }

    /**
    * 获取多个有序集合中的并集并存入指定有序集合中.返回保存到指定集合中的元素数量.
    * ZUnionStore destination numKeys key [key...] [WEIGHTS weight[weight...]] [AGGREGATE MIN| MAX| SUM]
    * @param key
    * @param keyWeight
    * @param destKey
    * @param keyInfos
    * @param aggregate
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zUnionAndStore(String key, int keyWeight, String destKey, Map<String, Integer> keyInfos,
            final RedisZSetCommands.Aggregate aggregate) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            int size = keyInfos.size();
            List<String> otherKeys = new ArrayList<>(size);
            final int[] weights = new int[size];
            weights[0] = keyWeight;
            int count = 1;
            for (Map.Entry<String, Integer> entry : keyInfos.entrySet()) {
                otherKeys.add(entry.getKey());
                weights[count] = entry.getValue();
                count++;
            }
            return customRedisTemplate.intersectAndStore(key, otherKeys, destKey, weights, aggregate);
        } catch (Exception e) {
            log.error("redis zUnionAndStore error : ", e);
            return -1L;
        }
    }

    /**
    * 删除有序集合中指定的元素. ZRem key member [member...]
    * @param key
    * @param members
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zRemove(String key, Object... members) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().remove(key, members);
        } catch (Exception e) {
            log.error("redis zRemove error : ", e);
            return -1L;
        }
    }

    /**
    * 删除有序集合内指定排名的元素. ZRemRangeByRank key start end.
    * @param key
    * @param start
    * @param end
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zRemoveRangeByRank(String key, Long start, Long end) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().removeRange(key, start, end);
        } catch (Exception e) {
            log.error("redis zRemove error : ", e);
            return -1L;
        }
    }

    /**
    * 删除有序集合内指定分数范围内的元素. ZRemRangeByScore key min max.
    * @param key
    * @param min
    * @param max
    * @return: java.lang.Long
    * @Author: wntl
    * @Date: 2020/11/18
    */
    public Long zRemoveRangeByScore(String key, Double min, Double max) {
        try {
            if (CommonUtils.isEmpty(key)) {
                return -1L;
            }
            return template.opsForZSet().removeRangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis zRemove error : ", e);
            return -1L;
        }
    }

    /* ====================以上为ZSet有序集合操作======================== */
}
