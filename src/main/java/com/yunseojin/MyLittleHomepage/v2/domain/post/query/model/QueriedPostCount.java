package com.yunseojin.MyLittleHomepage.v2.domain.post.query.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "post_counts")
public class QueriedPostCount {

    @Id
    @Column(name = "post_id")
    private Long id;

    @Basic
    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @Basic
    @Column(name = "comment_count", nullable = false)
    private Integer commentCount = 0;

    @Basic
    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @Basic
    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount = 0;
}