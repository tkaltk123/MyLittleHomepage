package com.yunseojin.MyLittleHomepage.domain.comment.command.validation;

import com.yunseojin.MyLittleHomepage.domain.comment.command.exception.CommentErrorMessage;
import com.yunseojin.MyLittleHomepage.domain.comment.command.exception.CommentException;
import com.yunseojin.MyLittleHomepage.domain.post.query.repository.QueriedPostRepository;
import com.yunseojin.MyLittleHomepage.domain.comment.query.model.QueriedComment;
import com.yunseojin.MyLittleHomepage.domain.comment.query.repository.QueriedCommentRepository;
import com.yunseojin.MyLittleHomepage.domain.post.command.exception.PostErrorMessage;
import com.yunseojin.MyLittleHomepage.domain.post.command.exception.PostException;
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
