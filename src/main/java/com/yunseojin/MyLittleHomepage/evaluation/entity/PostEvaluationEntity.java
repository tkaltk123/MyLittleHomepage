package com.yunseojin.MyLittleHomepage.evaluation.entity;

import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE evaluations SET is_deleted = 1 WHERE id=?")
@DiscriminatorValue("post")
public class PostEvaluationEntity extends EvaluationEntity {
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;
}