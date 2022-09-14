package com.yunseojin.MyLittleHomepage.etc.interceptor;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.etc.service.JwtService;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RestLoginInterceptor extends LoginInterceptor {

    @Autowired
    public RestLoginInterceptor(JwtService jwtService, MemberService memberService) {

        super(jwtService, memberService);
    }

    @Override
    protected String getToken(HttpServletRequest request, String tokenName) {

        String bearerToken = request.getHeader(tokenName);

        if (bearerToken == null)
            return null;

        if (!bearerToken.startsWith("Bearer "))
            throw new BadRequestException(ErrorMessage.INVALID_TOKEN_EXCEPTION);

        return bearerToken.substring(7);
    }

    @Override
    protected void tokenExpired(String accessToken, HttpServletRequest request, HttpServletResponse response) {

        throw new BadRequestException(ErrorMessage.EXPIRED_TOKEN_EXCEPTION);
    }
}
