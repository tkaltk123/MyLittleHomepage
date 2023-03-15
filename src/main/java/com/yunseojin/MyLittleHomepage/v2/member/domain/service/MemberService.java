package com.yunseojin.MyLittleHomepage.v2.member.domain.service;

import com.yunseojin.MyLittleHomepage.v2.auth.domain.UserDetails;
import com.yunseojin.MyLittleHomepage.v2.auth.service.AuthenticationPrincipal;
import com.yunseojin.MyLittleHomepage.v2.member.domain.exception.MemberErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.member.domain.exception.MemberException;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.v2.member.domain.validation.nickname.UniqueNickname;
import com.yunseojin.MyLittleHomepage.v2.member.domain.validation.password.PasswordConstraint;
import com.yunseojin.MyLittleHomepage.v2.member.domain.validation.username.UniqueUsername;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService implements AuthenticationPrincipal {

    private final MemberRepository repository;

    @UniqueUsername
    @UniqueNickname
    public Member create(@PasswordConstraint Member member) {
        member.onCreated();
        return member;
    }

    @UniqueUsername
    @UniqueNickname
    public Member update(Member member, @PasswordConstraint Member newMember,
            String currentPassword) {
        validatePassword(member, currentPassword);
        member.update(newMember);
        return member;
    }

    private void validatePassword(Member member, String currentPassword) {
        if (member.isWrongPassword(currentPassword)) {
            throw new MemberException(MemberErrorMessage.INCORRECT_PASSWORD_EXCEPTION);
        }
    }

    public void delete(Member member, String currentPassword) {
        validatePassword(member, currentPassword);
        member.delete();
    }

    public UserDetails login(String userName, String password) {
        var member = getByUsername(userName);
        if (member != null && !member.isWrongPassword(password)) {
            throw new MemberException(MemberErrorMessage.LOGIN_FAILED_EXCEPTION);
        }
        return member;
    }

    @Override
    public UserDetails getByUsername(String username) {
        var member = repository.getByUsername(username);
        if (member == null) {
            throw new MemberException(MemberErrorMessage.NOT_EXISTS_EXCEPTION);
        }
        return member;
    }
}
