package com.yunseojin.MyLittleHomepage.domain.feedback.query.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
@DiscriminatorValue("post")
public class QueriedPostFeedback extends QueriedFeedback {

    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;
}