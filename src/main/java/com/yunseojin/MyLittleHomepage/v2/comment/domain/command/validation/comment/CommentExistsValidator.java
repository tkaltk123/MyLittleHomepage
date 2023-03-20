package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.validation.comment;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.aggregete.Comment;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.repository.QueriedCommentRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentExistsValidator implements ConstraintValidator<CommentExists, Comment> {

    private final QueriedCommentRepository repository;

    @Override
    public boolean isValid(Comment comment, ConstraintValidatorContext context) {
        return repository.existsById(comment.getId());
    }
}
