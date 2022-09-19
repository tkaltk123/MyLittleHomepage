package com.yunseojin.MyLittleHomepage.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_counts")
public class PostCount {

    @Id
    private Long id;

    @MapsId
    @Setter
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private PostEntity post;

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