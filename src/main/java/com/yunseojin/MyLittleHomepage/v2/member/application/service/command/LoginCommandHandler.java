package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import auth.dto.Token;
import auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.LoginCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class LoginCommandHandler implements CommandHandler<LoginCommand, Token> {

    private final JwtTokenProvider tokenProvider;

    @Override
    public Token handle(LoginCommand command) {
        return tokenProvider.login(command.getUsername(), command.getPassword());
    }
}
