package com.yunseojin.MyLittleHomepage.v2.member.domain;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.validation.Create;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.validation.Update;
import com.yunseojin.MyLittleHomepage.v2.member.domain.exception.MemberErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.member.domain.exception.MemberException;
import com.yunseojin.MyLittleHomepage.v2.member.domain.validation.nickname.UniqueNickname;
import com.yunseojin.MyLittleHomepage.v2.member.domain.validation.username.UniqueUsername;
import javax.validation.Valid;
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
    @Validated(Create.class)
    public Member create(@Valid MemberVo memberVo) {
        return new Member(memberVo);
    }

    @UniqueUsername
    @UniqueNickname
    @Validated(Update.class)
    public Member update(Member member, @Valid MemberVo memberVo,
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
