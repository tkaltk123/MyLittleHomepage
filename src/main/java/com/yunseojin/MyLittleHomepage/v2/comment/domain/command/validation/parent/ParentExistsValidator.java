package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.validation.parent;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.vo.CommentVo;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.repository.QueriedCommentRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParentExistsValidator implements ConstraintValidator<ParentExists, CommentVo> {

    private final QueriedCommentRepository repository;

    @Override
    public boolean isValid(CommentVo commentVo, ConstraintValidatorContext context) {
        return repository.existsById(commentVo.getParentId());
    }
}
