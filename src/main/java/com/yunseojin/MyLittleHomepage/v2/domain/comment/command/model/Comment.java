package com.yunseojin.MyLittleHomepage.v2.domain.comment.command.model;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.v2.domain.comment.command.event.CommentCreatedEvent;
import com.yunseojin.MyLittleHomepage.v2.domain.comment.command.event.CommentDeletedEvent;
import com.yunseojin.MyLittleHomepage.v2.domain.comment.command.event.CommentUpdatedEvent;
import com.yunseojin.MyLittleHomepage.v2.domain.comment.query.model.QueriedComment;
import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.model.BaseAggregateRoot;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SQLDelete(sql = "UPDATE comments SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted = 0")
@Table(name = "comments")
public class Comment extends BaseAggregateRoot<Comment> {

    @Column(name = "post_id", nullable = false)
    private Long postId;


    @Column(name = "writer_id", nullable = false)
    private Long writerId;

    @Column(name = "writer_name", nullable = false)
    private String writerName;

    @Column(name = "parent_id")
    private Long parentId;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToOne(mappedBy = "comment", optional = false, cascade = CascadeType.PERSIST)
    private CommentCountV2 commentCount;

    //글로벌 적용했는데 BatchSize 안붙이니 동작 안함
    @BatchSize(size = 100)
    @OrderBy("id asc")
    @OneToMany
    @JoinColumn(name = "parent_id")
    private final List<CommentEntity> children = new ArrayList<>();

    protected Comment(QueriedComment commentInfo) {
        this.postId = commentInfo.getPostId();
        this.writerId = commentInfo.getWriterId();
        this.writerName = commentInfo.getWriterName();
        this.parentId = commentInfo.getParentId();
        this.content = commentInfo.getContent();
        this.commentCount = new CommentCountV2(this);
        this.registerEvent(new CommentCreatedEvent(readOnly()));
    }

    protected Comment update(QueriedComment commentInfo) {
        this.writerName = commentInfo.getWriterName();
        updateContent(commentInfo.getContent());
        this.registerEvent(new CommentUpdatedEvent(readOnly()));
        return this;
    }

    private void updateContent(String content) {
        if (Objects.nonNull(content)) {
            this.content = content;
        }
    }

    @Override
    protected void delete() {
        super.delete();
        this.registerEvent(new CommentDeletedEvent(readOnly()));
    }

    public QueriedComment readOnly() {
        return CommentConverter.INSTANCE.readOnly(this);
    }

    @Mapper
    interface CommentConverter {

        CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);

        QueriedComment readOnly(Comment comment);
    }
}