package com.yunseojin.MyLittleHomepage.application.auth.service.command;

import com.yunseojin.MyLittleHomepage.application.auth.dto.command.RefreshCommand;
import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.domain.auth.service.AuthService;
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
        return service.refresh(command.getBearerToken());
    }
}
