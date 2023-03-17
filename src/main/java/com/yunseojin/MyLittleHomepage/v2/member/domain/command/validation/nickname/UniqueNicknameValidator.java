package com.yunseojin.MyLittleHomepage.v2.member.domain.command.validation.nickname;

import com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.repository.QueriedMemberRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueNicknameValidator implements ConstraintValidator<UniqueNickname, Member> {

    private final QueriedMemberRepository repository;

    @Override
    public boolean isValid(Member member, ConstraintValidatorContext context) {
        return isUniqueNickname(member);
    }

    private boolean isUniqueNickname(Member member) {
        Long memberId = getMemberId(member);
        return !repository.existsByNicknameAndIdIsNot(member.getNickname(), memberId);
    }

    private Long getMemberId(Member member) {
        Long id = member.getId();
        if (id == null) {
            return -1L;
        }
        return id;
    }
}
