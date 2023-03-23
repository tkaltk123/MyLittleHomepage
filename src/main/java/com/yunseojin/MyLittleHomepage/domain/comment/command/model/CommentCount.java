package com.yunseojin.MyLittleHomepage.domain.comment.command.model;

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
@Table(name = "comment_counts")
public class CommentCount {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    private Comment comment;

    @Basic
    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @Basic
    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount = 0;

    public CommentCount(Comment comment) {
        this.comment = comment;
    }
}