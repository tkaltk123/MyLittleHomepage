package com.yunseojin.MyLittleHomepage.domain.feedback.command.model;

import com.yunseojin.MyLittleHomepage.domain.contract.command.model.BaseAggregateRoot;
import com.yunseojin.MyLittleHomepage.domain.feedback.query.model.QueriedFeedback;
import com.yunseojin.MyLittleHomepage.domain.feedback.vo.FeedbackType;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "feedbacks")
@DiscriminatorColumn(name = "dtype")
public abstract class Feedback extends BaseAggregateRoot<Feedback> {

    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_type", nullable = false)
    protected FeedbackType feedbackType;

    @Column(name = "writer_id", nullable = false)
    protected Long writerId;

    protected Feedback(QueriedFeedback feedbackInfo) {
        this.feedbackType = feedbackInfo.getFeedbackType();
        this.writerId = feedbackInfo.getWriterId();
    }

    protected Feedback apply(FeedbackType feedbackType) {
        applyFeedbackType(feedbackType);
        return this;
    }

    private void applyFeedbackType(FeedbackType feedbackType) {
        if (Objects.isNull(feedbackType)) {
            return;
        }
        if (Objects.equals(feedbackType, this.feedbackType)) {
            feedbackType = feedbackType.reverse();
        }
        this.feedbackType = feedbackType;
    }

    public abstract QueriedFeedback readOnly();

}