package org.mine.aplt.exception;

import org.mine.aplt.util.CommonUtils;

import java.util.Properties;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: RedisException
 * @date 2020/11/2619:47
 */
public class RedisException extends MineBizException {

    private static final Properties PROPERTIES = loadProperties("config/properties/error/error.properties");

    public RedisException(String error_code, String error_msg) {
        super(error_code, error_msg);
    }

    /**
    * redis中键 [%s]不存在
    * @param keyName
    * @return: org.mine.aplt.exception.MineBizException
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public static MineBizException REDIS_NOT_FOUNT(String... keyName) {
        return newException(PROPERTIES, "REDIS_NOT_FOUNT", new Object[]{CommonUtils.toString(keyName)});
    }

    /**
    * 增加redis键[%s]失败
    * @param errorMsg
    * @return: org.mine.aplt.exception.MineBizException
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public static MineBizException REDIS_ADD(String errorMsg) {
        return newException(PROPERTIES, "REDIS_ADD", new Object[]{ errorMsg });
    }

    /**
    * 修改redis键[%s]失败
    * @param errorMsg
    * @return: org.mine.aplt.exception.MineBizException
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public static MineBizException REDIS_MODIFY(String errorMsg) {
        return newException(PROPERTIES, "REDIS_MODIFY", new Object[]{ errorMsg });
    }

    /**
    * 删除redis键[%s]失败
    * @param errorMsg
    * @return: org.mine.aplt.exception.MineBizException
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public static MineBizException REDIS_DEL(String errorMsg) {
        return newException(PROPERTIES, "REDIS_DEL", new Object[]{ errorMsg });
    }

    /**
    * redis连接异常
    * @return: org.mine.aplt.exception.MineBizException
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public static MineBizException REDIS_CONNECT() {
        return newException(PROPERTIES, "REDIS_CONNECT", new Object[]{ });
    }

    /**
    * redis执行LUA失败。失败信息: %s
    * @param errorMsg
    * @return: org.mine.aplt.exception.MineBizException
    * @Author: wntl
    * @Date: 2020/11/26
    */
    public static MineBizException REDIS_EVAL_ERROR(String errorMsg) {
        return newException(PROPERTIES, "REDIS_EVAL_ERROR", new Object[]{ errorMsg });
    }
}
