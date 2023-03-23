package com.yunseojin.MyLittleHomepage.domain.comment.command.model;

import com.yunseojin.MyLittleHomepage.domain.comment.command.validation.CommentValidator;
import com.yunseojin.MyLittleHomepage.domain.comment.query.model.QueriedComment;
import com.yunseojin.MyLittleHomepage.domain.contract.command.validation.Create;
import com.yunseojin.MyLittleHomepage.domain.contract.command.validation.Update;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentValidator validator;

    @Validated(Create.class)
    public Comment create(@Valid QueriedComment commentInfo) {

        validator.validatePost(commentInfo);
        validator.validateParent(commentInfo);
        return new Comment(commentInfo);
    }

    @Validated(Update.class)
    public Comment update(@NotNull Comment comment, @Valid QueriedComment commentInfo) {

        validator.validateWriter(comment.getWriterId(), comment.getWriterId());
        return comment.update(commentInfo);
    }

    public void delete(@NotNull Comment comment, Long memberId) {

        validator.validateWriter(comment.getWriterId(), memberId);
        comment.delete();
    }
}
