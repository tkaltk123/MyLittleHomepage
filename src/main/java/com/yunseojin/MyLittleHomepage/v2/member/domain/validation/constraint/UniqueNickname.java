package com.yunseojin.MyLittleHomepage.v2.member.domain.validation.constraint;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.member.domain.validation.validator.UniqueNicknameValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueNicknameValidator.class)
@Target(PARAMETER)
@Retention(RUNTIME)
@Documented
public @interface UniqueNickname {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    ErrorMessage exception() default ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION;
}
