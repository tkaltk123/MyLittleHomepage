package com.yunseojin.MyLittleHomepage.comment.entity;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@SQLDelete(sql = "UPDATE comments SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted = 0")
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

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

    @OneToOne(mappedBy = "comment", optional = false, cascade = CascadeType.PERSIST)
    private CommentCount commentCount;

    //글로벌 적용했는데 BatchSize 안붙이니 동작 안함
    @BatchSize(size = 100)
    @Builder.Default
    @OrderBy("id asc")
    @OneToMany(mappedBy = "parent")
    private List<CommentEntity> children = new ArrayList<>();

    public CommentEntity withCommentCount() {

        commentCount = new CommentCount();
        commentCount.setComment(this);
        return this;
    }

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
}