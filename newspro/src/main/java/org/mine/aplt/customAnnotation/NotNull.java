package org.mine.aplt.customAnnotation;

import java.lang.annotation.*;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: NotNull
 * @date 2020/11/2319:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_PARAMETER})
@Documented
public @interface NotNull {
}
