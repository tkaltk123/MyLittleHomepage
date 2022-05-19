package com.yunseojin.MyLittleHomepage.evaluation.entity;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE EVALUATIONS SET IS_DELETED = 1 WHERE ID=?")
@DiscriminatorValue("COMMENT")
public class CommentEvaluationEntity extends EvaluationEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "COMMENT_ID", nullable = false)
    private CommentEntity comment;

    public void setComment(CommentEntity comment) {
        if (this.comment != null)
            this.comment.getEvaluations().remove(this);
        this.comment = comment;
        if (comment != null)
            comment.getEvaluations().add(this);
    }
}