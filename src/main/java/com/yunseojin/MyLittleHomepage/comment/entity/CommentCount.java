package com.yunseojin.MyLittleHomepage.comment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

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
    @OneToOne
    private CommentEntity comment;

    @Basic
    @Setter
    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @Basic
    @Setter
    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount = 0;
}