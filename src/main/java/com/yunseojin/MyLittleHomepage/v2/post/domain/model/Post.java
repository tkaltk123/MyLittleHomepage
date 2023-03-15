package com.yunseojin.MyLittleHomepage.v2.post.domain.model;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.model.BaseAggregateRoot;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public Post withPostCount() {

        postCount = new PostCountV2();
        postCount.setPost(this);
        return this;
    }
}