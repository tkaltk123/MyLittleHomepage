package com.yunseojin.MyLittleHomepage.v2.member.domain.command.validation.password;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = HasValidPasswordValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface HasValidPassword {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
