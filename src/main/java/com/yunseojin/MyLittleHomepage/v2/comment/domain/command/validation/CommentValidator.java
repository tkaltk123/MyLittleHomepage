package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.validation;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.exception.CommentErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.exception.CommentException;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.entity.QueriedComment;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.repository.QueriedCommentRepository;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.exception.PostErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.exception.PostException;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.repository.QueriedPostRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidator {

    private final QueriedPostRepository postRepository;

    private final QueriedCommentRepository repository;

    public void validatePost(QueriedComment comment) {

        if (!postRepository.existsById(comment.getPostId())) {
            throw new PostException(PostErrorMessage.NOT_EXISTS_EXCEPTION);
        }
    }

    public void validateParent(QueriedComment comment) {

        if (!isNullOrExistsParent(comment.getParentId())) {
            throw new CommentException(CommentErrorMessage.PARENT_NOT_EXISTS_EXCEPTION);
        }
    }

    private boolean isNullOrExistsParent(Long parentId) {
        return Objects.isNull(parentId) || repository.existsById(parentId);
    }

    public void validateWriter(Long writerId, Long memberId) {

        if (!Objects.equals(writerId, memberId)) {
            throw new CommentException(CommentErrorMessage.NOT_WRITER_EXCEPTION);
        }
    }
}
