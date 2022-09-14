package com.yunseojin.MyLittleHomepage.etc.resolver;

import com.yunseojin.MyLittleHomepage.etc.annotation.MemberToken;
import com.yunseojin.MyLittleHomepage.member.dto.MemberTokenDto;
import com.yunseojin.MyLittleHomepage.etc.service.JwtService;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import com.yunseojin.MyLittleHomepage.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class MemberTokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;
    private final MemberService memberService;

    @Value("${jwt.access.name}")
    private String accessTokenName;

    @Value("${jwt.refresh.name}")
    private String refreshTokenName;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isMemberToken = parameter
                .getParameterAnnotation(MemberToken.class) != null;

        boolean isMemberTokenDto = MemberTokenDto.class.equals(parameter.getParameterType());

        return isMemberToken && isMemberTokenDto;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        var request = (HttpServletRequest) webRequest.getNativeRequest();
        var accessToken = CookieUtil.getToken(request, accessTokenName);
        switch (jwtService.validateAccessToken(accessToken)) {

            case EXPIRE:
                var refreshToken = CookieUtil.getToken(request, refreshTokenName);
                try {
                    accessToken = memberService.refreshAccessToken(accessToken, refreshToken);
                } catch (Exception ignore) {
                    break;
                }
            case VALID:
                return jwtService.getMemberTokenDtoFrom(accessToken);
            case INVALID:
                break;
        }
        return null;
    }
}