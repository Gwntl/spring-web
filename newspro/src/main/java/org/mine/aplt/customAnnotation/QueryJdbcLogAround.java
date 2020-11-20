package org.mine.aplt.customAnnotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryJdbcLogAround {
}
