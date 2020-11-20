package org.mine.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.mine.aplt.exception.MineException;
import org.mine.aplt.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 当一个切面存在Around,Before,After时. 执行顺序为: Around before -> Before -> Around after -> After.
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: ClassLogPrint
 * @date 2020/9/2810:19
 */
@Aspect
@Component
public class ClassLogPrint {
    final Logger logger = LoggerFactory.getLogger(ClassLogPrint.class);

    @Pointcut("execution(* org.mine.service.impl.async.*.*(..))")
    public void pointcut(){}

    /**
     * 使用<code>@annotation</code>表达式来表示当前切面只在方法上面使用指定注解时才执行.
     */
    @Pointcut("@annotation(org.mine.aplt.customAnnotation.AspectLogBefore)")
    public void pointcutLogBeforeAnnotation(){}

    @Pointcut("@annotation(org.mine.aplt.customAnnotation.AspectLogAround)")
    public void pointcutLogAroundAnnotation(){}

    @Pointcut("@annotation(org.mine.aplt.customAnnotation.AspectLogAfter)")
    public void pointcutLogAfterAnnotation(){}

//    @Pointcut("@annotation(org.mine.aplt.customAnnotation.QueryJdbcLogAround)")
//    public void queryByJdbcTemplate(){}

//    @Pointcut("execution(* org.mine.quartz.run.job.JobRecodeLogLogic.*(..))")
//    public void pointcut_1(){}

    /**
     * 使用||号来分割多个切面.
     * @param point 执行对象信息
     * @return
     */
//    @Around("pointcut() || pointcut_1()")
//    @Around("pointcut()")
    @Around("pointcutLogAroundAnnotation()")
    public Object logAround(ProceedingJoinPoint point) {
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        logger.debug("{}.{} begin,[args : {}]>>>>>>>>>>>>>>>>>>", className, methodName, CommonUtils.toStringNoTitle(point.getArgs()));
        Object obj = null;
        try {
            //实际业务处理过程
            obj = point.proceed();
        } catch (Throwable throwable) {
            logger.error(MineException.getStackTrace(throwable));
        }
        logger.debug("{}.{} end, [result : {}]>>>>>>>>>>>>>>>>>>", className, methodName, obj);
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Class<?> returnType = signature.getReturnType();
//        if (obj == null && returnType.isPrimitive()) {
//
//        }
        return obj;
    }

    /**
     * Order标志同一个切面上存在多个代理时, 执行顺序. 值越小最先执行
     */
//    @Before("pointcut()")
//    @Order(1)
//    public void logBefore(){
//        logger.debug("logBefore....................");
//    }

//    @Before("pointcut()")
//    @Order(2)
//    public void logBefore_1(){
//        logger.debug("logBefore_1....................");
//    }

//    @After("pointcut()")
//    public void logAfter(){
//        logger.debug("logAfter....................");
//    }
}
