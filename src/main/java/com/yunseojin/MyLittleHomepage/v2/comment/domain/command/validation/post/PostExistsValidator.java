package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.validation.post;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.aggregete.Comment;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.repository.QueriedPostRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostExistsValidator implements ConstraintValidator<PostExists, Comment> {

    private final QueriedPostRepository postRepository;

    @Override
    public boolean isValid(Comment comment, ConstraintValidatorContext context) {
        return postRepository.existsById(comment.getPostId());
    }
}
