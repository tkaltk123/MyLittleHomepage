package com.yunseojin.MyLittleHomepage.v2.member.domain.command.validation.username;

import com.yunseojin.MyLittleHomepage.v2.member.domain.query.repository.QueriedMemberRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final QueriedMemberRepository repository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return isUniqueLoginId(username);
    }

    private boolean isUniqueLoginId(String username) {
        return !repository.existsByUsername(username);
    }
}
