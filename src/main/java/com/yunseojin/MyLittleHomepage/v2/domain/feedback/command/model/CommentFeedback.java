package com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.model;

import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.event.comment.CommentFeedbackAppliedEvent;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.event.comment.CommentFeedbackCreatedEvent;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedCommentFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.vo.FeedbackType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE feedbacks SET is_deleted = 1 WHERE id=?")
@DiscriminatorValue("comment")
public class CommentFeedback extends Feedback {

    @Column(name = "comment_id", nullable = false)
    protected Long commentId;

    protected CommentFeedback(QueriedCommentFeedback feedbackInfo) {
        super(feedbackInfo);
        this.commentId = feedbackInfo.getCommentId();
        this.registerEvent(new CommentFeedbackCreatedEvent(readOnly()));
    }

    @Override
    protected Feedback apply(FeedbackType feedbackType) {
        super.apply(feedbackType);
        this.registerEvent(new CommentFeedbackAppliedEvent(readOnly()));
        return this;
    }

    @Override
    public QueriedFeedback readOnly() {
        return CommentFeedbackConverter.INSTANCE.readOnly(this);
    }

    @Mapper
    interface CommentFeedbackConverter {

        CommentFeedbackConverter INSTANCE = Mappers.getMapper(CommentFeedbackConverter.class);

        QueriedCommentFeedback readOnly(CommentFeedback feedback);
    }
}