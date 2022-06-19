package com.yunseojin.MyLittleHomepage.comment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment_counts")
public class CommentCount {

    @Id
    private Long id;

    @MapsId
    @Setter
    @OneToOne(optional = false)
    private CommentEntity comment;

    @Basic
    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @Basic
    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount = 0;

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