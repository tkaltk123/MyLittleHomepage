package com.yunseojin.MyLittleHomepage.v2.member.domain.command.validation.password;

import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HasValidPasswordValidator implements
        ConstraintValidator<HasValidPassword, QueriedMember> {

    private static final int MIN_SIZE = 8;

    private static final int MAX_SIZE = 20;

    public static final String OUT_OF_SIZE_MESSAGE = "비밀번호는 {MIN_SIZE}~{MAX_SIZE}글자 입니다.";

    private static final String PATTERN = "[a-zA-Z0-9~!@#$%^&*()]+";

    private static final String UN_MATCHES_MESSAGE = "비밀번호는 알파벳, 숫자, 특수문자(~!@#$%^&*())만 사용할 수 있습니다.";

    @Override
    public boolean isValid(QueriedMember memberInfo, ConstraintValidatorContext context) {
        var password = memberInfo.getPassword();
        var outOfSize = outOfSize(password.length());
        var unMatches = unMatches(password);
        context.disableDefaultConstraintViolation();
        if (outOfSize) {
            context.buildConstraintViolationWithTemplate(OUT_OF_SIZE_MESSAGE)
                    .addConstraintViolation();
        }
        if (unMatches) {
            context.buildConstraintViolationWithTemplate(UN_MATCHES_MESSAGE)
                    .addConstraintViolation();
        }

        return !outOfSize && !unMatches;
    }

    private boolean outOfSize(int length) {
        return length < MIN_SIZE || length > MAX_SIZE;
    }

    private boolean unMatches(String password) {
        return !password.matches(PATTERN);
    }
}
