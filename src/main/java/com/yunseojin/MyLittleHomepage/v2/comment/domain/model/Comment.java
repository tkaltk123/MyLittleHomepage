package com.yunseojin.MyLittleHomepage.v2.comment.domain.model;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.BaseAggregateRoot;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "UPDATE comments SET is_deleted = 1 WHERE id=?")
@Table(name = "comments")
public class Comment extends BaseAggregateRoot<Comment> {

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "writer_id", nullable = false)
    private Long writerId;

    @Column(name = "writer_name", nullable = false)
    private String writerName;

    @Setter(AccessLevel.PRIVATE)
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToOne(mappedBy = "comment", optional = false, cascade = CascadeType.PERSIST)
    private CommentCountV2 commentCount;

    //글로벌 적용했는데 BatchSize 안붙이니 동작 안함
    @BatchSize(size = 100)
    @OrderBy("id asc")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public Comment withCommentCount() {

        commentCount = new CommentCountV2();
        commentCount.setComment(this);
        return this;
    }

    public void addChild(@NotNull Comment child) {

        this.children.add(child);
        child.setParent(this);
    }

    public void removeChild(@NotNull Comment child) {
        this.children.remove(child);
    }
}