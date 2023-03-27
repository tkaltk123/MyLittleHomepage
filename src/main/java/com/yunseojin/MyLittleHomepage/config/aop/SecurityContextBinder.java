package com.yunseojin.MyLittleHomepage.config.aop;

import com.yunseojin.MyLittleHomepage.domain.auth.service.SecurityContextAccessor;
import com.yunseojin.MyLittleHomepage.domain.member.query.model.MemberContainer;
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

    @Around("execution(* handle( com.yunseojin.MyLittleHomepage.domain.member.query.model.MemberContainer+))")
    public Object bindMember(ProceedingJoinPoint joinPoint) throws Throwable {
        var authOperation = getMemberContainer(joinPoint);
        var member = contextAccessor.getMember();
        if (Objects.nonNull(authOperation) && Objects.nonNull(member)) {
            authOperation.setMember(member);
        }
        return joinPoint.proceed(new Object[]{authOperation});
    }

    private MemberContainer getMemberContainer(JoinPoint joinPoint) {
        var object = joinPoint.getArgs()[0];
        if (Objects.isNull(object)) {
            return null;
        }

        return (MemberContainer) object;
    }
}
