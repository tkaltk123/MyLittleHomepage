package com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.aggregate.BaseAggregateRoot;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.event.PostCreatedEvent;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.event.PostDeletedEvent;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.event.PostUpdatedEvent;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.entity.QueriedPost;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "UPDATE posts SET is_deleted = 1 WHERE id=?")
@Table(name = "posts")
public class Post extends BaseAggregateRoot<Post> {

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "writer_id", nullable = false)
    private Long writerId;

    @Column(name = "writer_name", nullable = false)
    private String writerName;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToOne(mappedBy = "post", optional = false, cascade = CascadeType.PERSIST)
    private PostCountV2 postCount;

    protected Post(QueriedPost postInfo) {
        this.boardId = postInfo.getBoardId();
        this.writerId = postInfo.getWriterId();
        this.writerName = postInfo.getWriterName();
        this.title = postInfo.getTitle();
        this.content = postInfo.getContent();
        this.postCount = new PostCountV2(this);
        this.registerEvent(new PostCreatedEvent(readOnly()));
    }

    protected Post update(QueriedPost postInfo) {
        this.writerName = postInfo.getWriterName();
        updateTitle(postInfo.getTitle());
        updateContent(postInfo.getContent());
        this.registerEvent(new PostUpdatedEvent(readOnly()));
        return this;
    }

    private void updateTitle(String title) {
        if (Objects.nonNull(title)) {
            this.title = title;
        }
    }

    private void updateContent(String content) {
        if (Objects.nonNull(content)) {
            this.content = content;
        }
    }

    @Override
    protected void delete() {
        super.delete();
        this.registerEvent(new PostDeletedEvent(readOnly()));
    }

    public QueriedPost readOnly() {
        return PostConverter.INSTANCE.readOnly(this);
    }

    @Mapper
    interface PostConverter {

        PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

        @Mapping(target = "board.id", source = "boardId")
        QueriedPost readOnly(Post post);
    }
}