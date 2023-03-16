package com.yunseojin.MyLittleHomepage.v2.post.domain.validation.board;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = BoardExistsValidator.class)
@Target({METHOD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface BoardExists {


    String message() default "게시판이 존재하지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
