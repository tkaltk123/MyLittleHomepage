package com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.model;

import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.event.post.PostFeedbackAppliedEvent;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.event.post.PostFeedbackCreatedEvent;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedPostFeedback;
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
@DiscriminatorValue("post")
public class PostFeedback extends Feedback {

    @Column(name = "post_id", nullable = false)
    protected Long postId;

    protected PostFeedback(QueriedPostFeedback feedbackInfo) {
        super(feedbackInfo);
        this.postId = feedbackInfo.getPostId();
        this.registerEvent(new PostFeedbackCreatedEvent(readOnly()));
    }

    @Override
    protected Feedback apply(FeedbackType feedbackType) {
        super.apply(feedbackType);
        this.registerEvent(new PostFeedbackAppliedEvent(readOnly()));
        return this;
    }

    @Override
    public QueriedFeedback readOnly() {
        return PostFeedbackConverter.INSTANCE.readOnly(this);
    }

    @Mapper
    interface PostFeedbackConverter {

        PostFeedbackConverter INSTANCE = Mappers.getMapper(PostFeedbackConverter.class);

        QueriedPostFeedback readOnly(PostFeedback feedback);
    }
}