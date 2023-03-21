package com.yunseojin.MyLittleHomepage.v2.auth.application.aop;

import com.yunseojin.MyLittleHomepage.v2.auth.domain.service.SecurityContextAccessor;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.AuthOperation;
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

    @Around("execution(* handle( com.yunseojin.MyLittleHomepage.v2.member.application.dto.AuthOperation+))")
    public Object bindMember(ProceedingJoinPoint joinPoint) throws Throwable {
        var authOperation = getAuthOperation(joinPoint);
        if (Objects.nonNull(authOperation)) {
            authOperation.setMember(contextAccessor.getMember());
        }
        return joinPoint.proceed(new Object[]{authOperation});
    }

    private AuthOperation getAuthOperation(JoinPoint joinPoint) {
        var object = joinPoint.getArgs()[0];
        if (Objects.isNull(object)) {
            return null;
        }

        return (AuthOperation) object;
    }
}
