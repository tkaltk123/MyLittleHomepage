package com.yunseojin.MyLittleHomepage.etc.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;

@Component
@Aspect
@Slf4j
public class MethodName {

    @Around(value = "execution(* com.yunseojin.MyLittleHomepage.*.service.*.*(..))")
    public Object printMethodName(ProceedingJoinPoint joinPoint) throws Throwable {

        var methodName = joinPoint.getSignature().getName();

        log.debug(methodName + " start");

        var result = joinPoint.proceed();

        log.debug(methodName + " end");

        return result;
    }

    @AfterThrowing(value = "execution(* com.yunseojin.MyLittleHomepage.*.service.*.*(..))")
    public void printMethodName(JoinPoint joinPoint) {

        var methodName = joinPoint.getSignature().getName();
        log.debug(methodName + " except");
    }
}
