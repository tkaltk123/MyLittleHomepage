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
    @OneToOne(optional = false)
    private PostEntity post;

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

    public void increaseCommentCount() {
        ++this.commentCount;
    }

    public void decreaseCommentCount() {
        --this.commentCount;
    }

    public void increaseViewCount() {
        ++this.viewCount;
    }

    public void increaseLikeCount() {
        ++this.likeCount;
    }

    public void decreaseLikeCount() {
        --this.likeCount;
    }

    public void increaseDislikeCount() {
        ++this.dislikeCount;
    }

    public void decreaseDislikeCount() {
        --this.dislikeCount;
    }
}