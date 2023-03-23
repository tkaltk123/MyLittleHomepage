package com.yunseojin.MyLittleHomepage.v2.domain.member.command.model;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.validation.Create;
import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.validation.Update;
import com.yunseojin.MyLittleHomepage.v2.domain.member.command.validation.MemberValidator;
import com.yunseojin.MyLittleHomepage.v2.domain.member.query.model.QueriedMember;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberValidator validator;

    @Validated(Create.class)
    public Member create(@Valid QueriedMember memberInfo) {
        validator.validateUsername(memberInfo, null);
        validator.validateNickname(memberInfo, null);
        return new Member(memberInfo);
    }

    public void validatePassword(QueriedMember member, String currentPassword) {
        validator.validatePassword(member, currentPassword);
    }

    @Validated(Update.class)
    public Member update(Member member, @Valid QueriedMember memberInfo) {
        validator.validateUsername(memberInfo, member.getUsername());
        validator.validateNickname(memberInfo, member.getNickname());
        return member.update(memberInfo);
    }

    public void delete(Member member) {
        member.delete();
    }

}
