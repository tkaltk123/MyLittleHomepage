package com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate;

import com.yunseojin.MyLittleHomepage.v2.member.domain.command.exception.MemberErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.exception.MemberException;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.validation.password.HasValidPassword;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {


    public Member create(@HasValidPassword QueriedMember memberInfo) {
        return new Member(memberInfo);
    }

    public void validatePassword(QueriedMember member, String currentPassword) {
        if (member.isWrongPassword(currentPassword)) {
            throw new MemberException(MemberErrorMessage.INCORRECT_PASSWORD_EXCEPTION);
        }
    }

    public Member update(Member member, @HasValidPassword QueriedMember memberInfo) {
        return member.update(memberInfo);
    }

    public void delete(Member member) {
        member.delete();
    }
}
