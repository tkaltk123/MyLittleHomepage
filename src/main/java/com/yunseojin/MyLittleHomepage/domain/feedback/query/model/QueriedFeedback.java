package com.yunseojin.MyLittleHomepage.domain.feedback.query.model;

import com.yunseojin.MyLittleHomepage.domain.feedback.vo.FeedbackType;
import com.yunseojin.MyLittleHomepage.domain.contract.query.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "feedbacks")
@DiscriminatorColumn(name = "dtype")
public abstract class QueriedFeedback extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_type", nullable = false)
    protected FeedbackType feedbackType;

    @NotNull
    @Column(name = "writer_id", nullable = false)
    protected Long writerId;
}