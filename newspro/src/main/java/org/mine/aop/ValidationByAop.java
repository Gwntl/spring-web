package org.mine.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mine.aplt.customAnnotation.NotEmpty;
import org.mine.aplt.customAnnotation.NotNull;
import org.mine.aplt.customAnnotation.ParamValue;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: ValidationByAop
 * @date 2020/11/2319:41
 */
@Aspect
@Component
public class ValidationByAop {

    public static final LocalVariableTableParameterNameDiscoverer pnd = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("@annotation(org.mine.aplt.customAnnotation.ParamValidation)")
    public void paramValidation(){}

    @Pointcut("@annotation(org.mine.aplt.customAnnotation.NotNull)")
    public void notNull() {}

    @Pointcut("@annotation(org.mine.aplt.customAnnotation.NotEmpty)")
    public void notEmpty() {}

    @Before("paramValidation() || notNull() || notEmpty()")
    public void before(JoinPoint point) {
        System.out.println("coming.............");
        // 获取参数注解
        MethodSignature signature = (MethodSignature)point.getSignature();
        System.out.println("method return type: " + signature.getReturnType());
        Method method = signature.getMethod();
        System.out.println("method : " + method.getName());
        Annotation[][] annotations = method.getParameterAnnotations();
        // 获取方法参数名
        String[] pns = pnd.getParameterNames(method);
        // 获取入参
        Object[] args = point.getArgs();

        for (int i = 0; i < annotations.length; i++) {
            Object arg = args[i];
            String paramName = pns[i];
            Annotation[] as = annotations[i];
            for (Annotation a : as) {
                parseAnnotation(a, arg, paramName);
            }
        }
    }

    public void parseAnnotation(Annotation a, Object arg, String paramName) {
        if (a != null && a.annotationType().equals(NotNull.class) && arg == null) {
            System.out.println(paramName + "入参[" + arg + "]不能为null");
        } else if (a != null && a.annotationType().equals(NotEmpty.class)) {
            if (arg == null || (arg.getClass().equals(String.class) && ((String)arg).length() <= 0)) {
                System.out.println(paramName + "入参[" + arg + "]不能为空");
            }
        } else if (a != null && a.annotationType().equals(ParamValue.class)) {
            if (arg == null) {
                if (arg.getClass().equals(String.class)) {
                    System.out.println(paramName + "入参[" + arg + "]类型错误.");
                } else if (arg.getClass().equals(Double.class)) {

                } else if (arg.getClass().equals(Long.class)) {

                } else if (arg.getClass().equals(Integer.class)) {

                }
            }
        }
    }
}
