package com.yunseojin.MyLittleHomepage.evaluation.entity;

import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
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
@DiscriminatorValue("POST")
public class PostEvaluationEntity extends EvaluationEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "POST_ID", nullable = false)
    private PostEntity post;

    public void setPost(PostEntity post) {
        if (this.post != null)
            this.post.getEvaluations().remove(this);
        this.post = post;
        if (post != null)
            post.getEvaluations().add(this);
    }
}