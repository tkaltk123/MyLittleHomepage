package com.yunseojin.MyLittleHomepage.v2.application.auth.service.command;

import com.yunseojin.MyLittleHomepage.v2.application.auth.dto.command.LoginCommand;
import com.yunseojin.MyLittleHomepage.v2.application.auth.dto.response.TokenResponse;
import com.yunseojin.MyLittleHomepage.v2.application.auth.mapper.TokenMapper;
import com.yunseojin.MyLittleHomepage.v2.domain.auth.service.AuthService;
import com.yunseojin.MyLittleHomepage.v2.application.contract.service.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class LoginCommandHandler implements CommandHandler<LoginCommand, TokenResponse> {

    private final AuthService service;

    private final TokenMapper mapper;

    @Override
    public TokenResponse handle(LoginCommand command) {
        var token = service.login(command.getUsername(), command.getPassword());
        return mapper.toResponse(token);
    }
}
