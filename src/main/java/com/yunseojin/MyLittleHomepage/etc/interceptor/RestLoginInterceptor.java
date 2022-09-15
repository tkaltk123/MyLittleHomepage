package com.yunseojin.MyLittleHomepage.etc.interceptor;

import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.TokenValidationType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.etc.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class RestLoginInterceptor implements HandlerInterceptor {

    @Value("${jwt.access.name}")
    protected String accessTokenName;

    private final JwtService jwtService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Login login = handlerMethod.getMethod().getDeclaredAnnotation(Login.class);

        var accessToken = getToken(request, accessTokenName);
        var validationType = jwtService.validateAccessToken(accessToken);

        //annotation 없으면 검증 생략
        if (login == null)
            return true;

        //로그인을 요구하고 토큰이 유효하지 않은 경우
        if (login.required() && validationType == TokenValidationType.INVALID)
            throw new BadRequestException(ErrorMessage.NOT_LOGIN_EXCEPTION);

        if (!login.required() && validationType == TokenValidationType.VALID)
            throw new BadRequestException(ErrorMessage.ALREADY_LOGIN_EXCEPTION);

        if (login.required() && validationType == TokenValidationType.EXPIRE) {
            throw new BadRequestException(ErrorMessage.EXPIRED_TOKEN_EXCEPTION);
        }

        return true;
    }

    private String getToken(HttpServletRequest request, String tokenName) {

        String bearerToken = request.getHeader(tokenName);

        if (bearerToken == null)
            return null;

        if (!bearerToken.startsWith("Bearer "))
            throw new BadRequestException(ErrorMessage.INVALID_TOKEN_EXCEPTION);

        return bearerToken.substring(7);
    }
}
