package com.yunseojin.MyLittleHomepage.evaluation.entity;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE EVALUATIONS SET IS_DELETED = 1 WHERE ID=?")
@DiscriminatorValue("COMMENT")
public class CommentEvaluationEntity extends EvaluationEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "COMMENT_ID", nullable = false)
    private CommentEntity comment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private MemberEntity writer;
}