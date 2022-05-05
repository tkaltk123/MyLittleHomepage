package com.yunseojin.MyLittleHomepage.evaluation.entity;

import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE EVALUATIONS SET IS_DELETED = 1 WHERE ID=?")
@DiscriminatorValue("POST")
public class PostEvaluationEntity extends EvaluationEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "POST_ID", nullable = false)
    private PostEntity post;

    @ManyToOne(optional = false)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private MemberEntity writer;
}