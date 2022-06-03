package com.yunseojin.MyLittleHomepage.etc.aop;

import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Aspect
public class LoginAop {
    @Resource
    private MemberInfo memberInfo;


    @Before("@annotation(com.yunseojin.MyLittleHomepage.etc.annotation.Login)")
    public void checkLogin(JoinPoint joinPoint) throws BadRequestException {
        var methodSignature = (MethodSignature) joinPoint.getSignature();
        var login = methodSignature.getMethod().getAnnotation(Login.class);
        var requireLoggedIn = login.required();
        var notLoggedIn = memberInfo.getId() == null;

        if (requireLoggedIn && notLoggedIn)
            throw new BadRequestException(ErrorMessage.NOT_LOGIN_EXCEPTION);

        else if (!requireLoggedIn && !notLoggedIn)
            throw new BadRequestException(ErrorMessage.ALREADY_LOGIN_EXCEPTION);
    }
}