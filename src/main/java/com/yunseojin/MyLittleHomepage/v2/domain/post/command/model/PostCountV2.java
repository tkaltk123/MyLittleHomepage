package com.yunseojin.MyLittleHomepage.v2.domain.post.command.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Table(name = "post_counts")
public class PostCountV2 {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    private Post post;

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

    public PostCountV2(Post post) {
        this.post = post;
    }
}