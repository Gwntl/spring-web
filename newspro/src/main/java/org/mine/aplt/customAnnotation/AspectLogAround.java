package org.mine.aplt.customAnnotation;

import java.lang.annotation.*;

/**
 * 日志打印. 在进入方法前和方法后打印日志
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: AspectAround
 * @date 2020/9/2815:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AspectLogAround {
}
