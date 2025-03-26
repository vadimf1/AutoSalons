package org.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class RepositoryLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryLoggingAspect.class);

    @Pointcut("execution(* org.example.repository..*(..))")
    public void repositoryMethods() {}

    @Before("repositoryMethods()")
    public void logBeforeRepositoryMethods(JoinPoint joinPoint) {
        Class<?> repositoryInterface = joinPoint.getTarget().getClass().getInterfaces()[0];
        String repositoryName = repositoryInterface.getSimpleName();

        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        logger.info("Executing {}.{} with parameters: {}", repositoryName, methodName, Arrays.toString(args));
    }

    @AfterReturning(value = "repositoryMethods()", returning = "result")
    public void logAfterReturningRepositoryMethods(JoinPoint joinPoint, Object result) {
        Class<?> repositoryInterface = joinPoint.getTarget().getClass().getInterfaces()[0];
        String repositoryName = repositoryInterface.getSimpleName();

        String methodName = joinPoint.getSignature().getName();
        logger.info("Executed {}.{} successfully. Result: {}", repositoryName, methodName, result);
    }

    @AfterThrowing(value = "execution(* org.example.repository..*(..))", throwing = "ex")
    public void logAfterThrowingRepositoryMethods(JoinPoint joinPoint, Throwable ex) {
        Class<?> repositoryInterface = joinPoint.getTarget().getClass().getInterfaces()[0];
        String repositoryName = repositoryInterface.getSimpleName();

        String methodName = joinPoint.getSignature().toShortString();
        logger.error("Error in {}.{}: {}", repositoryName, methodName, ex.getMessage(), ex);
    }
}
