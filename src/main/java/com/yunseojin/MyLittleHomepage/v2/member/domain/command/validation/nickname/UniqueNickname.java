package com.yunseojin.MyLittleHomepage.v2.member.domain.command.validation.nickname;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueNicknameValidator.class)
@Target({METHOD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface UniqueNickname {

    String message() default "닉네임은 중복될 수 없습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
