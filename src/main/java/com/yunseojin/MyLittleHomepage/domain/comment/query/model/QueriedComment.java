package com.yunseojin.MyLittleHomepage.domain.comment.query.model;

import com.yunseojin.MyLittleHomepage.domain.contract.query.model.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "posts")
public class QueriedComment extends BaseEntity {

    @NotNull
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @NotNull
    @Column(name = "writer_id", nullable = false)
    private Long writerId;

    @NotNull
    @Column(name = "writer_name", nullable = false)
    private String writerName;

    @Column(name = "parent_id")
    private Long parentId;

    @NotNull
    @Lob
    @Column(name = "content")
    private String content;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "comment_id")
    private QueriedCommentCount commentCount;

    @BatchSize(size = 100)
    @OrderBy("id asc")
    @OneToMany
    @JoinColumn(name = "parent_id")
    private final List<QueriedComment> children = new ArrayList<>();
}