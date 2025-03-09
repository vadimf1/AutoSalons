package org.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggableAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggableAspect.class);

    @Around("@annotation(org.example.aop.Loggable)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.info("Executing method: {}", joinPoint.getSignature());

        try {
            Object result = joinPoint.proceed();
            long timeTaken = System.currentTimeMillis() - startTime;
            logger.info("Method {} executed in {} ms", joinPoint.getSignature(), timeTaken);
            return result;
        } catch (Exception e) {
            logger.error("Exception in method {}: {}", joinPoint.getSignature(), e.getMessage(), e);
            throw e;
        }
    }
}
