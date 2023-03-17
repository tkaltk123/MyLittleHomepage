package com.yunseojin.MyLittleHomepage.v2.post.domain.command.validation.post;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PostExistsValidator.class)
@Target({METHOD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface PostExists {


    String message() default "게시글이 존재하지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
