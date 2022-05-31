package com.yunseojin.MyLittleHomepage.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "POST_COUNTS")
public class PostCount {
    @Id
    private Long id;

    @MapsId
    @Setter
    @OneToOne(optional = false)
    private PostEntity post;

    @Basic
    @Column(name = "VIEW_COUNT", nullable = false)
    private Integer viewCount = 0;

    @Basic
    @Column(name = "COMMENT_COUNT", nullable = false)
    private Integer commentCount = 0;

    @Basic
    @Column(name = "LIKE_COUNT", nullable = false)
    private Integer likeCount = 0;

    @Basic
    @Column(name = "DISLIKE_COUNT", nullable = false)
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