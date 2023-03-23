package com.yunseojin.MyLittleHomepage.v2.config.aop;

import com.yunseojin.MyLittleHomepage.v2.application.member.dto.MemberContainer;
import com.yunseojin.MyLittleHomepage.v2.domain.auth.service.SecurityContextAccessor;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SecurityContextBinder {

    private final SecurityContextAccessor contextAccessor;

    @Around("execution(* handle( com.yunseojin.MyLittleHomepage.v2.application.member.dto.AuthOperation+))")
    public Object bindMember(ProceedingJoinPoint joinPoint) throws Throwable {
        var authOperation = getAuthOperation(joinPoint);
        if (Objects.nonNull(authOperation)) {
            authOperation.setMember(contextAccessor.getMember());
        }
        return joinPoint.proceed(new Object[]{authOperation});
    }

    private MemberContainer getAuthOperation(JoinPoint joinPoint) {
        var object = joinPoint.getArgs()[0];
        if (Objects.isNull(object)) {
            return null;
        }

        return (MemberContainer) object;
    }
}
