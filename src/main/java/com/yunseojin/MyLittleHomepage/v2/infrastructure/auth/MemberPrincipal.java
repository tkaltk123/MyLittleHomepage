package com.yunseojin.MyLittleHomepage.v2.infrastructure.auth;

import auth.domain.UserDetails;
import auth.service.AuthenticationPrincipal;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.exception.MemberErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.exception.MemberException;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.repository.QueriedMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberPrincipal implements AuthenticationPrincipal {

    private final QueriedMemberRepository repository;

    @Override
    public UserDetails getByUsername(String username) {
        var member = repository.getByUsername(username);
        if (member == null) {
            throw new MemberException(MemberErrorMessage.NOT_EXISTS_EXCEPTION);
        }
        return member;
    }
}
