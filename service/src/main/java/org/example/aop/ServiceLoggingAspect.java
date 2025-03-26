package org.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    @Pointcut("execution(* org.example.service.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        logger.info("Executing {} with parameters: {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(value = "serviceMethods()", returning = "result")
    public void logAfterReturningServiceMethods(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Executed {} successfully. Result: {}", methodName, result);
    }

    @AfterThrowing(value = "serviceMethods()", throwing = "ex")
    public void logAfterThrowingServiceMethods(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.error("Error in {}: {}", methodName, ex.getMessage(), ex);
    }
}
