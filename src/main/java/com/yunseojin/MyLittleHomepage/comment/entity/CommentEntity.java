package com.yunseojin.MyLittleHomepage.comment.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.CommentEvaluationEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import lombok.*;
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
@SQLDelete(sql = "UPDATE COMMENTS SET IS_DELETED = 1 WHERE ID=?")
@Where(clause = "IS_DELETED = 0")
@Table(name = "COMMENTS")
@SecondaryTable(name = "COMMENT_COUNTS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "COMMENT_ID"))
public class CommentEntity extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "POST_ID", nullable = false)
    private PostEntity post;

    @ManyToOne(optional = false)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private MemberEntity writer;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private CommentEntity parent;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Builder.Default
    @Column(name = "LIKE_COUNT", table = "COMMENT_COUNTS", nullable = false)
    private Integer likeCount = 0;

    @Builder.Default
    @Column(name = "DISLIKE_COUNT", table = "COMMENT_COUNTS", nullable = false)
    private Integer dislikeCount = 0;

    @Setter(AccessLevel.NONE)
    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> children = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEvaluationEntity> evaluations = new ArrayList<>();
}