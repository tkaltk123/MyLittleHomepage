package com.yunseojin.MyLittleHomepage.v2.post.domain.validation.post;

import com.yunseojin.MyLittleHomepage.v2.post.domain.model.Post;
import com.yunseojin.MyLittleHomepage.v2.post.domain.repository.PostRepositoryV2;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostExistsValidator implements ConstraintValidator<PostExists, Post> {

    private final PostRepositoryV2 repository;

    @Override
    public boolean isValid(Post post, ConstraintValidatorContext context) {
        return repository.existsById(post.getId());
    }
}
