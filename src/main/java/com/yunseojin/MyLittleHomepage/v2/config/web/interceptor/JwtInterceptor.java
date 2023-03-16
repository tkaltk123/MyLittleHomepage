package com.yunseojin.MyLittleHomepage.v2.config.web.interceptor;

import auth.service.JwtTokenProvider;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final String accessTokenName;

    private final String loginUserAttr;

    private final JwtTokenProvider tokenProvider;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String bearerToken = request.getHeader(accessTokenName);
        if (Objects.nonNull(bearerToken)) {
            var user = tokenProvider.getUser(bearerToken);
            request.setAttribute(loginUserAttr, user);
        }

        return true;
    }
}
