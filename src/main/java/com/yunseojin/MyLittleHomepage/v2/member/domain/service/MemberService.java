package com.yunseojin.MyLittleHomepage.v2.member.domain.service;

import com.yunseojin.MyLittleHomepage.v2.member.domain.exception.MemberErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.member.domain.exception.MemberException;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.MemberVo;
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
public class MemberService {


    @UniqueUsername
    @UniqueNickname
    public Member create(@PasswordConstraint MemberVo memberVo) {
        return new Member(memberVo);
    }

    @UniqueUsername
    @UniqueNickname
    public Member update(Member member, @PasswordConstraint MemberVo memberVo,
            String currentPassword) {
        validatePassword(member, currentPassword);
        member.update(memberVo);
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
}
