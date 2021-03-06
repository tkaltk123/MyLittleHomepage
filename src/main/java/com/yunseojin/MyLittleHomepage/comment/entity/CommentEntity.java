package com.yunseojin.MyLittleHomepage.comment.entity;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.Evaluable;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@SQLDelete(sql = "UPDATE comments SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted = 0")
@Table(name = "comments")
public class CommentEntity extends BaseEntity implements Evaluable {

    private static class CommentEntityBuilderImpl extends CommentEntityBuilder<CommentEntity, CommentEntityBuilderImpl> {

        @Override
        public CommentEntity build() {

            var comment = new CommentEntity(this);
            comment.getCommentCount().setComment(comment);

            return comment;
        }
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "writer_id", nullable = false)
    private MemberEntity writer;

    @Column(name = "writer_name", nullable = false)
    private String writerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommentEntity parent;

    @Lob
    @Column(name = "content")
    private String content;

    //즉시 로딩
    @OneToOne(mappedBy = "comment", optional = false, cascade = CascadeType.PERSIST)
    @Builder.Default
    private CommentCount commentCount = new CommentCount();

    //글로벌 적용했는데 BatchSize 안붙이니 동작 안함
    @BatchSize(size = 100)
    @Builder.Default
    @OrderBy("id asc")
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

    public void update(CommentRequest commentRequest) {

        content = commentRequest.getContent();
    }

    public Integer getLikeCount() {
        return commentCount.getLikeCount();
    }

    @Override
    public void increaseLikeCount() {
        commentCount.increaseLikeCount();
    }

    @Override
    public void decreaseLikeCount() {
        commentCount.decreaseLikeCount();
    }

    public Integer getDislikeCount() {
        return commentCount.getDislikeCount();
    }

    @Override
    public void increaseDislikeCount() {
        commentCount.increaseDislikeCount();
    }

    @Override
    public void decreaseDislikeCount() {
        commentCount.decreaseDislikeCount();
    }
}