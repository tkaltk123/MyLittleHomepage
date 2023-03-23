package com.yunseojin.MyLittleHomepage.domain.auth.service;

import com.yunseojin.MyLittleHomepage.domain.auth.vo.Token;
import org.springframework.security.core.Authentication;


public interface AuthService {

    Token login(String username, String password);

    String getToken(String bearerToken);

    Authentication getAuthentication(String accessToken);

    String refresh(String bearerRefreshToken);
}
