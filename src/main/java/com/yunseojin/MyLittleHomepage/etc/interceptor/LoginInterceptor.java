package com.yunseojin.MyLittleHomepage.etc.interceptor;

import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.TokenValidationType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.etc.service.JwtService;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import com.yunseojin.MyLittleHomepage.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${jwt.access.name}")
    protected String accessTokenName;

    @Value("${jwt.access.remain}")
    private Integer accessTokenRemainHour;

    @Value("${jwt.refresh.name}")
    protected String refreshTokenName;

    protected final JwtService jwtService;
    protected final MemberService memberService;

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
            tokenExpired(accessToken, request, response);
        }

        return true;
    }

    protected String getToken(HttpServletRequest request, String tokenName) {

        return CookieUtil.getToken(request, tokenName);
    }

    protected void tokenExpired(String accessToken, HttpServletRequest request, HttpServletResponse response) {

        var refreshToken = CookieUtil.getToken(request, refreshTokenName);
        try {

            var newToken = memberService.refreshAccessToken(accessToken, refreshToken);
            response.addCookie(CookieUtil.createTokenCookie(accessTokenName, accessTokenRemainHour, newToken));
        } catch (Exception ignore) {

            throw new BadRequestException(ErrorMessage.NOT_LOGIN_EXCEPTION);
        }
    }

}
