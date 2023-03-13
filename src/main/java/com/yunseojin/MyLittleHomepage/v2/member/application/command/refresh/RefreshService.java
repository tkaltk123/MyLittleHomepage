package com.yunseojin.MyLittleHomepage.v2.member.application.command.refresh;

import com.yunseojin.MyLittleHomepage.v2.auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.command.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class RefreshService implements CommandService<RefreshCommand, String> {

    private final JwtTokenProvider tokenProvider;

    @Override
    public String handle(RefreshCommand command) {
        return tokenProvider.refresh(command.getRefreshToken());
    }
}
