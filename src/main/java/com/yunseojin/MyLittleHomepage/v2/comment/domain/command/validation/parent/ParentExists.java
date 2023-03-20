package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.validation.parent;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ParentExistsValidator.class)
@Target({METHOD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface ParentExists {


    String message() default "답글을 작성할 댓글이 존재하지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
