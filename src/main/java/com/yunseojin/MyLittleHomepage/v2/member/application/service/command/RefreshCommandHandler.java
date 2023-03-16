package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.RefreshCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class RefreshCommandHandler implements CommandHandler<RefreshCommand, String> {

    private final JwtTokenProvider tokenProvider;

    @Override
    public String handle(RefreshCommand command) {
        return tokenProvider.refresh(command.getRefreshToken());
    }
}
