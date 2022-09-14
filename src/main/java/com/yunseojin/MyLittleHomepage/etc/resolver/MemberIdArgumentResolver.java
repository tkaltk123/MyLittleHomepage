package com.yunseojin.MyLittleHomepage.etc.resolver;

import com.yunseojin.MyLittleHomepage.etc.annotation.MemberId;
import com.yunseojin.MyLittleHomepage.etc.annotation.MemberToken;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.etc.service.JwtService;
import com.yunseojin.MyLittleHomepage.member.dto.MemberTokenDto;
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

@RequiredArgsConstructor
@Component
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    @Value("${jwt.access.name}")
    private String accessTokenName;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isMemberId = parameter
                .getParameterAnnotation(MemberId.class) != null;

        boolean isLong = Long.class.equals(parameter.getParameterType());

        return isMemberId && isLong;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        var bearerToken = webRequest.getHeader(accessTokenName);

        if (bearerToken == null)
            return null;

        if (!bearerToken.startsWith("Bearer "))
            throw new BadRequestException(ErrorMessage.INVALID_TOKEN_EXCEPTION);

        var accessToken = bearerToken.substring(7);
        switch (jwtService.validateAccessToken(accessToken)) {

            case EXPIRE:
                throw new BadRequestException(ErrorMessage.EXPIRED_TOKEN_EXCEPTION);
            case VALID:
                return jwtService.getMemberId(accessToken);
            case INVALID:
                throw new BadRequestException(ErrorMessage.INVALID_TOKEN_EXCEPTION);
        }
        return null;
    }
}