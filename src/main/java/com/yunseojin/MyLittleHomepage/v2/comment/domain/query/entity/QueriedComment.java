package com.yunseojin.MyLittleHomepage.v2.comment.domain.query.entity;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.entity.QueriedPost;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "")
@Table(name = "posts")
public class QueriedComment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private QueriedPost post;

    @Column(name = "writer_id", nullable = false)
    private Long writerId;

    @Column(name = "writer_name", nullable = false)
    private String writerName;

    @Column(name = "parent_id")
    private Long parentId;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToOne(mappedBy = "comment", optional = false)
    private QueriedCommentCount commentCount;

    @BatchSize(size = 100)
    @OrderBy("id asc")
    @OneToMany
    @JoinColumn(name = "parent_id")
    private final List<CommentEntity> children = new ArrayList<>();
}