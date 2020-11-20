package org.mine.rule.redis;

import org.mine.rule.Ruler;

/**
 * Redis缓存key命名,当key大于1024kb时会影响效率,因此在可读性保证的情况下不要太长. 系统名:模块名:业务逻辑表示:指定key:value类型</br>
 * Redis key 命名必须全部小写字母、数字、英文点号（.）和英文半角冒号（:）组成，必须以英文字母开头.</br>
 * 不要包含特殊字符，如下划线、空格、换行、单双引号以及其他转义字符.</br>
 * <p>
 * 可使用(主业务.具体内容；只能用小写字母)的形式进行命名.
 * 例: 1、短信验证码: wntl:newspro:phone.13109293827(手机号):code:string.
 *     2、数据库信息(user表, 主键为username. username为:test): wntl:newspro:db.user.test:info:string.
 * </p>
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: RedisKeyRuler
 * @date 2020/11/2014:35
 */
public class RedisKeyRuler implements Ruler {
    private static final String PROJECT_NAME = "wntl";
    private static final String BASE_DIR = System.getProperty("user.dir");
    private static final String MODULE_NAME = PROJECT_NAME.substring(BASE_DIR.lastIndexOf("\\") + 1).toLowerCase();
    private static final String SEPARATOR = ":";
    public static final String VALUE_TYPE_STRING = "string";
    public static final String VALUE_TYPE_LIST = "list";
    public static final String VALUE_TYPE_HASH = "hash";
    public static final String VALUE_TYPE_SET = "set";
    public static final String VALUE_TYPE_SORT_SET = "sortset";

    /**
    * 创建redisKey
    * @param logicDesc 业务逻辑描述
    * @param baseKeyName
    * @param valueType
    * @return: java.lang.String
    * @Author: wntl
    * @Date: 2020/11/20
    */
    public static String doCreateKey(String logicDesc, String baseKeyName, String valueType) {
        return new StringBuilder().append(PROJECT_NAME).append(SEPARATOR).append(MODULE_NAME)
                .append(SEPARATOR).append(logicDesc.toLowerCase()).append(SEPARATOR).append(baseKeyName.toLowerCase())
                .append(SEPARATOR).append(valueType).toString();
    }
}
