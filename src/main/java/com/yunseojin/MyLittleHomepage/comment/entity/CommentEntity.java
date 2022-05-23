package com.yunseojin.MyLittleHomepage.comment.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.Evaluable;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE COMMENTS SET IS_DELETED = 1 WHERE ID=?")
@Where(clause = "IS_DELETED = 0")
@Table(name = "COMMENTS")
@SecondaryTable(name = "COMMENT_COUNTS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "COMMENT_ID"))
public class CommentEntity extends BaseEntity implements Evaluable {
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "POST_ID", nullable = false)
    private PostEntity post;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private MemberEntity writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private CommentEntity parent;

    @Setter
    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Builder.Default
    @Column(name = "LIKE_COUNT", table = "COMMENT_COUNTS", nullable = false)
    private Integer likeCount = 0;

    @Builder.Default
    @Column(name = "DISLIKE_COUNT", table = "COMMENT_COUNTS", nullable = false)
    private Integer dislikeCount = 0;

    @Builder.Default
    @OrderBy("id asc")
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "parent")
    private List<CommentEntity> children = new ArrayList<>();

    public void setParent(CommentEntity parent) {
        if (this.parent != null)
            this.parent.getChildren().remove(this);
        this.parent = parent;
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    @Override
    public void increaseLikeCount() {
        ++this.likeCount;
    }

    @Override
    public void decreaseLikeCount() {
        --this.likeCount;
    }

    @Override
    public void increaseDislikeCount() {
        ++this.dislikeCount;
    }

    @Override
    public void decreaseDislikeCount() {
        --this.dislikeCount;
    }
}