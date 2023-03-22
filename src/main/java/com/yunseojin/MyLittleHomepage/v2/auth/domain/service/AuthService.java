package com.yunseojin.MyLittleHomepage.v2.auth.domain.service;

import com.yunseojin.MyLittleHomepage.v2.auth.domain.vo.Token;
import org.springframework.security.core.Authentication;


public interface AuthService {

    Token login(String username, String password);

    String getToken(String bearerToken);

    Authentication getAuthentication(String accessToken);

    String refresh(String refreshToken);
}
