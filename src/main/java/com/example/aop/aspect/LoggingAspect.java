package com.example.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(public * com.example.aop.service.*.*(..))")
    private void publicMethodsFromServicePackage() {}

    @Before(value = "publicMethodsFromServicePackage()")
    public void logBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        log.info("Executing {}.{} with params: {}", signature.getDeclaringType().getSimpleName(),
                signature.getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing(pointcut = "publicMethodsFromServicePackage()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        Signature signature = joinPoint.getSignature();
        log.error("Exception while executing {}.{}: {}", signature.getDeclaringType().getSimpleName(),
                signature.getName(),
                exception.getMessage());
    }

    @AfterReturning(pointcut = "publicMethodsFromServicePackage()", returning = "result")
    public void logAfterReturn(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        log.info("{}.{} executed successfully. Returns: {}", signature.getDeclaringType().getSimpleName(),
                signature.getName(),
                result);
    }
}
