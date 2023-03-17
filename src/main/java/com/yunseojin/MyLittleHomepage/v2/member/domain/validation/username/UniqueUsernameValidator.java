package com.yunseojin.MyLittleHomepage.v2.member.domain.validation.username;

import com.yunseojin.MyLittleHomepage.v2.member.domain.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.repository.MemberRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, Member> {

    private final MemberRepository repository;

    @Override
    public boolean isValid(Member member, ConstraintValidatorContext context) {
        return isUniqueLoginId(member);
    }

    private boolean isUniqueLoginId(Member member) {
        Long memberId = getMemberId(member);
        return !repository.existsByUsernameAndIdIsNot(member.getUsername(), memberId);
    }

    private Long getMemberId(Member member) {
        Long id = member.getId();
        if (id == null) {
            return -1L;
        }
        return id;
    }
}
