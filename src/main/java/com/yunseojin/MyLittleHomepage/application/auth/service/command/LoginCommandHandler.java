package com.yunseojin.MyLittleHomepage.application.auth.service.command;

import com.yunseojin.MyLittleHomepage.domain.auth.service.AuthService;
import com.yunseojin.MyLittleHomepage.application.auth.dto.command.LoginCommand;
import com.yunseojin.MyLittleHomepage.application.auth.dto.response.TokenResponse;
import com.yunseojin.MyLittleHomepage.application.auth.mapper.TokenMapper;
import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
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
