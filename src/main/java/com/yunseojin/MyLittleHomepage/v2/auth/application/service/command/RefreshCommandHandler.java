package com.yunseojin.MyLittleHomepage.v2.auth.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.auth.application.dto.command.RefreshCommand;
import com.yunseojin.MyLittleHomepage.v2.auth.domain.service.AuthService;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class RefreshCommandHandler implements CommandHandler<RefreshCommand, String> {

    private final AuthService service;

    @Override
    public String handle(RefreshCommand command) {
        return service.refresh(command.getRefreshToken());
    }
}
