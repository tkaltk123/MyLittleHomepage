package com.yunseojin.MyLittleHomepage.v2.post.domain.command.validation.post;

import com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete.Post;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.repository.QueriedPostRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostExistsValidator implements ConstraintValidator<PostExists, Post> {

    private final QueriedPostRepository repository;

    @Override
    public boolean isValid(Post post, ConstraintValidatorContext context) {
        return repository.existsById(post.getId());
    }
}
