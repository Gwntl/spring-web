package org.mine.aplt.customAnnotation;

import java.lang.annotation.*;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: MinValue
 * @date 2020/11/269:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface ParamValue {
    long[] paramValue();
}
