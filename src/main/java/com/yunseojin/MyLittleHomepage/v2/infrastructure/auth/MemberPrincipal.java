package com.yunseojin.MyLittleHomepage.v2.infrastructure.auth;

import auth.domain.UserDetails;
import auth.service.AuthenticationPrincipal;
import com.yunseojin.MyLittleHomepage.v2.member.domain.exception.MemberErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.member.domain.exception.MemberException;
import com.yunseojin.MyLittleHomepage.v2.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberPrincipal implements AuthenticationPrincipal {

    private final MemberRepository repository;

    @Override
    public UserDetails getByUsername(String username) {
        var member = repository.getByUsername(username);
        if (member == null) {
            throw new MemberException(MemberErrorMessage.NOT_EXISTS_EXCEPTION);
        }
        return member;
    }
}
