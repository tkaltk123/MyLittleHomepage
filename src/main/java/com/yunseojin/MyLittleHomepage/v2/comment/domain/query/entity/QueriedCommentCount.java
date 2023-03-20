package com.yunseojin.MyLittleHomepage.v2.comment.domain.query.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_counts")
public class QueriedCommentCount {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    private QueriedComment comment;

    @Basic
    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @Basic
    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount = 0;
}