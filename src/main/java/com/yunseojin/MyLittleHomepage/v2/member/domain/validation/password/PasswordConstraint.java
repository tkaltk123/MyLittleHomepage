package com.yunseojin.MyLittleHomepage.v2.member.domain.validation.password;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target(PARAMETER)
@Retention(RUNTIME)
@Documented
public @interface PasswordConstraint {

    String message() default "비밀번호는 8~20글자 사이의 알파벳, 숫자, 특수문자(~!@#$%^&*())입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
