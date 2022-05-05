package com.yunseojin.MyLittleHomepage.post.entity;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.CommentEvaluationEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.PostEvaluationEntity;
import com.yunseojin.MyLittleHomepage.hashtag.entity.HashtagEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE POSTS SET IS_DELETED = 1 WHERE ID=?")
@Where(clause = "IS_DELETED = 0")
@Table(name = "POSTS")
public class PostEntity extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private BoardEntity board;

    @ManyToOne(optional = false)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private MemberEntity writer;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "TITLE_HEAD")
    private String titleHead;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Basic
    @Builder.Default
    @Column(name = "VIEW_COUNT", nullable = false)
    private Integer viewCount = 0;

    @Basic
    @Builder.Default
    @Column(name = "COMMENT_COUNT", nullable = false)
    private Integer commentCount = 0;

    @Basic
    @Builder.Default
    @Column(name = "LIKE_COUNT", nullable = false)
    private Integer likeCount = 0;

    @Basic
    @Builder.Default
    @Column(name = "DISLIKE_COUNT", nullable = false)
    private Integer dislikeCount = 0;

    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashtagEntity> hashtags = new ArrayList<>();

    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostEvaluationEntity> evaluations = new ArrayList<>();
}