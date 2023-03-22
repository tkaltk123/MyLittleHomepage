package com.yunseojin.MyLittleHomepage.v2.board.domain.command.validation.name;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueNameValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface UniqueName {

    String message() default "게시판 이름은 중복될 수 없습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
