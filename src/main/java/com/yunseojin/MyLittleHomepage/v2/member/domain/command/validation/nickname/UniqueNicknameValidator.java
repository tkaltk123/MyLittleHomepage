package com.yunseojin.MyLittleHomepage.v2.member.domain.command.validation.nickname;

import com.yunseojin.MyLittleHomepage.v2.member.domain.query.repository.QueriedMemberRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueNicknameValidator implements ConstraintValidator<UniqueNickname, String> {

    private final QueriedMemberRepository repository;

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {
        return isUniqueNickname(nickname);
    }

    private boolean isUniqueNickname(String nickname) {
        return !repository.existsByNickname(nickname);
    }
}
