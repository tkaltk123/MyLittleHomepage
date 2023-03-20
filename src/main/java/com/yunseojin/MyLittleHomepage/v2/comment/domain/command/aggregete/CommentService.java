package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.aggregete;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.exception.CommentErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.exception.CommentException;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.validation.comment.CommentExists;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.validation.parent.ParentExists;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.validation.post.PostExists;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.vo.CommentVo;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.validation.Create;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.validation.Update;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class CommentService {

    @PostExists
    @Validated(Create.class)
    public Comment create(@Valid @ParentExists CommentVo commentVo) {
        return new Comment(commentVo);
    }

    @Validated(Update.class)
    public Comment update(@CommentExists Comment comment, @Valid CommentVo commentVo) {

        validateWriter(comment.getWriterId(), commentVo.getWriterId());
        comment.update(commentVo);
        return comment;
    }

    private void validateWriter(Long writerId, Long memberId) {

        if (!Objects.equals(writerId, memberId)) {
            throw new CommentException(CommentErrorMessage.NOT_WRITER_EXCEPTION);
        }
    }

    public void delete(@CommentExists Comment comment, Long memberId) {

        validateWriter(comment.getWriterId(), memberId);
        comment.delete();
    }
}
