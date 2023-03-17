package com.yunseojin.MyLittleHomepage.v2.post.domain.query.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.v2.board.domain.query.entity.QueriedBoard;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "")
@Table(name = "posts")
public class QueriedPost extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private QueriedBoard board;

    @Column(name = "writer_id", nullable = false)
    private Long writerId;

    @Column(name = "writer_name", nullable = false)
    private String writerName;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToOne(mappedBy = "post", optional = false)
    private QueriedPostCount postCount;
}