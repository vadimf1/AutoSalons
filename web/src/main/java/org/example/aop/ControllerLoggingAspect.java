package org.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void logBeforeControllerMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("Incoming request: {} with parameters: {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(value = "controllerMethods()", returning = "response")
    public void logAfterReturningControllerMethods(JoinPoint joinPoint, Object response) {
        String methodName = joinPoint.getSignature().toShortString();
        if (response instanceof ResponseEntity<?> responseEntity) {
            log.info("Response from {}: Status {}, Body {}", methodName, responseEntity.getStatusCode(), responseEntity.getBody());
        } else {
            log.info("Response from {}: {}", methodName, response);
        }
    }

    @AfterThrowing(value = "controllerMethods()", throwing = "ex")
    public void logAfterThrowingControllerMethods(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("Error in {}: {}", methodName, ex.getMessage(), ex);
    }
}
