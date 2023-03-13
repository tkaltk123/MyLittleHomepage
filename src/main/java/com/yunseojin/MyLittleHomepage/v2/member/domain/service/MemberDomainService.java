package com.yunseojin.MyLittleHomepage.v2.member.domain.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.v2.auth.domain.UserDetails;
import com.yunseojin.MyLittleHomepage.v2.auth.service.AuthenticationPrincipal;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.repository.MemberRepository;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberDomainService implements AuthenticationPrincipal {

    private final MemberRepository repository;

    public Member create(@NotNull Member member) {
        member.onCreated();
        return member;
    }

    public Member update(Member member, @NotNull Member newMember,
            String currentPassword) {
        validatePassword(member, currentPassword);
        member.update(newMember);
        return member;
    }

    private void validatePassword(@NotNull Member member, String currentPassword) {
        if (member.isWrongPassword(currentPassword)) {
            throw new BadRequestException(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION);
        }
    }

    public void delete(@NotNull Member member, String currentPassword) {
        validatePassword(member, currentPassword);
        member.delete();
    }

    public UserDetails login(String userName, String password) {
        var member = getByUsername(userName);
        if (member != null && !member.isWrongPassword(password)) {
            throw new BadRequestException(ErrorMessage.LOGIN_FAILED_EXCEPTION);
        }
        return member;
    }

    @Override
    public UserDetails getByUsername(String username) {
        var member = repository.getByUsername(username);
        if (member == null) {
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);
        }
        return member;
    }
}
