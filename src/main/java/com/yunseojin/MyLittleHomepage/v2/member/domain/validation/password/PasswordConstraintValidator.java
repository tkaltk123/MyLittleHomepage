package com.yunseojin.MyLittleHomepage.v2.member.domain.validation.password;

import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PasswordConstraintValidator implements
        ConstraintValidator<PasswordConstraint, Member> {

    private static final int MIN_LENGTH = 8;

    private static final int MAX_LENGTH = 20;

    private static final String PATTERN = "[a-zA-Z0-9~!@#$%^&*()]+";


    @Override
    public boolean isValid(Member member, ConstraintValidatorContext context) {
        return isValidPassword(member.getPassword());
    }

    private boolean isValidPassword(String password) {
        return Objects.nonNull(password) &&
                inRange(password.length()) &&
                password.matches(PATTERN);
    }

    private boolean inRange(int length) {
        return MIN_LENGTH <= length && length <= MAX_LENGTH;
    }
}
