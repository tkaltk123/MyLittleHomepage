package com.yunseojin.MyLittleHomepage.v2.member.application.command.login;

import com.yunseojin.MyLittleHomepage.v2.auth.dto.Token;
import com.yunseojin.MyLittleHomepage.v2.auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.command.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class LoginService implements CommandService<LoginCommand, Token> {

    private final JwtTokenProvider tokenProvider;

    @Override
    public Token handle(LoginCommand command) {
        var token = tokenProvider.login(command.getUsername(), command.getPassword());
        return new Token(token.getAccessToken(), token.getRefreshToken());
    }
}
