package com.yunseojin.MyLittleHomepage.v2.post.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_counts")
public class PostCountV2 {

    @Id
    private Long id;

    @MapsId
    @Setter
    @OneToOne
    private Post post;

    @Basic
    @Setter
    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @Basic
    @Setter
    @Column(name = "comment_count", nullable = false)
    private Integer commentCount = 0;

    @Basic
    @Setter
    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @Basic
    @Setter
    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount = 0;
}