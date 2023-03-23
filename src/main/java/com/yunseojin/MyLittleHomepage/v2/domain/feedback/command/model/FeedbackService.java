package com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.model;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.validation.Create;
import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.validation.Update;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.validation.FeedbackValidator;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedCommentFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedPostFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.vo.FeedbackType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackValidator validator;

    @Validated(Create.class)
    public Feedback create(@Valid QueriedFeedback feedbackInfo) {
        if (feedbackInfo instanceof QueriedPostFeedback) {
            return createPostFeedback((QueriedPostFeedback) feedbackInfo);
        }
        return createCommentFeedback((QueriedCommentFeedback) feedbackInfo);
    }

    private PostFeedback createPostFeedback(QueriedPostFeedback feedbackInfo) {

        validator.validatePost(feedbackInfo);
        return new PostFeedback(feedbackInfo);
    }

    private CommentFeedback createCommentFeedback(QueriedCommentFeedback feedbackInfo) {

        validator.validateComment(feedbackInfo);
        return new CommentFeedback(feedbackInfo);
    }

    @Validated(Update.class)
    public Feedback apply(@NotNull Feedback feedback, FeedbackType feedbackType) {
        return feedback.apply(feedbackType);
    }
}
