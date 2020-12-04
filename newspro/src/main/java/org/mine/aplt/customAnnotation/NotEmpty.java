package org.mine.aplt.customAnnotation;

import java.lang.annotation.*;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: NotEmpty
 * @date 2020/11/269:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface NotEmpty {
}
