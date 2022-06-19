package com.yunseojin.MyLittleHomepage.etc.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodName {

    @Before(value = "execution(* com.yunseojin.MyLittleHomepage.*.service.*.*(..))")
    public void printMethodName(JoinPoint joinPoint) {

        System.out.println();
        System.out.print("===========================");
        System.out.println(joinPoint.getSignature().getName());
    }
}
