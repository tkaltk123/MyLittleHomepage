package com.yunseojin.MyLittleHomepage.v2.application.auth.service.command;

import com.yunseojin.MyLittleHomepage.v2.application.auth.dto.command.RefreshCommand;
import com.yunseojin.MyLittleHomepage.v2.domain.auth.service.AuthService;
import com.yunseojin.MyLittleHomepage.v2.application.contract.service.CommandHandler;
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
