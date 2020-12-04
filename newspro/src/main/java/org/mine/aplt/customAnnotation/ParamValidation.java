package org.mine.aplt.customAnnotation;

import java.lang.annotation.*;

/**
 * 代表参数校验注解
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: ParamValidation
 * @date 2020/11/249:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ParamValidation {
}
