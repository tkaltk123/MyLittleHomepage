package com.yunseojin.MyLittleHomepage.comment.entity;

import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "COMMENT_COUNTS")
public class CommentCount {
    @Id
    private Long id;

    @MapsId
    @Setter
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private CommentEntity comment;

    @Basic
    @Column(name = "LIKE_COUNT", nullable = false)
    private Integer likeCount = 0;

    @Basic
    @Column(name = "DISLIKE_COUNT", nullable = false)
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