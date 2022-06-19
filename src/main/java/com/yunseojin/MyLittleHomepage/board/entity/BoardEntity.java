package com.yunseojin.MyLittleHomepage.board.entity;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostCount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@SQLDelete(sql = "UPDATE BOARDS SET IS_DELETED = 1 WHERE ID=?")
@Where(clause = "IS_DELETED = 0")
@Table(name = "BOARDS")
public class BoardEntity extends BaseEntity {

    private static class BoardEntityBuilderImpl extends BoardEntityBuilder<BoardEntity, BoardEntityBuilderImpl> {

        @Override
        public BoardEntity build() {

            var board = new BoardEntity(this);
            board.getBoardCount().setBoard(board);

            return board;
        }
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    //즉시 로딩
    @Builder.Default
    @OneToOne(mappedBy = "board", optional = false, cascade = CascadeType.PERSIST)
    private BoardCount boardCount = new BoardCount();

    public Integer getPostCount() {
        return boardCount.getPostCount();
    }

    public void increasePostCount() {
        boardCount.setPostCount(boardCount.getPostCount() + 1);
    }

    public void decreasePostCount() {
        boardCount.setPostCount(boardCount.getPostCount() - 1);
    }
}