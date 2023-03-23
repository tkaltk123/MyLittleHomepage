package com.yunseojin.MyLittleHomepage.domain.post.query.model;

import com.yunseojin.MyLittleHomepage.domain.board.query.model.QueriedBoard;
import com.yunseojin.MyLittleHomepage.domain.contract.command.validation.Create;
import com.yunseojin.MyLittleHomepage.domain.contract.query.model.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "posts")
public class QueriedPost extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private QueriedBoard board;

    @NotNull
    @Column(name = "writer_id", nullable = false)
    private Long writerId;

    @NotNull
    @Column(name = "writer_name", nullable = false)
    private String writerName;

    @NotNull(groups = {Create.class})
    @Size(min = 2, max = 255, message = "제목은 2~255글자 입니다")
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "post_id")
    private QueriedPostCount postCount;

    public Long getBoardId() {
        if (Objects.isNull(board)) {
            return null;
        }
        return board.getId();
    }
}