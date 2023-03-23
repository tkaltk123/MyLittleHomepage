package com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.validation;

import com.yunseojin.MyLittleHomepage.v2.domain.comment.command.exception.CommentErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.domain.comment.command.exception.CommentException;
import com.yunseojin.MyLittleHomepage.v2.domain.comment.query.repository.QueriedCommentRepository;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedCommentFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedPostFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.post.command.exception.PostErrorMessage;
import com.yunseojin.MyLittleHomepage.v2.domain.post.command.exception.PostException;
import com.yunseojin.MyLittleHomepage.v2.domain.post.query.repository.QueriedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackValidator {

    private final QueriedPostRepository postRepository;

    private final QueriedCommentRepository commentRepository;

    public void validatePost(QueriedPostFeedback feedback) {

        if (!postRepository.existsById(feedback.getPostId())) {
            throw new PostException(PostErrorMessage.NOT_EXISTS_EXCEPTION);
        }
    }

    public void validateComment(QueriedCommentFeedback feedback) {

        if (!commentRepository.existsById(feedback.getCommentId())) {
            throw new CommentException(CommentErrorMessage.NOT_EXISTS_EXCEPTION);
        }
    }
}