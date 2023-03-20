package com.yunseojin.MyLittleHomepage.v2.board.domain.command.aggregate;


import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.aggregate.BaseAggregateRoot;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@SQLDelete(sql = "UPDATE boards SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted = 0")
@Table(name = "boards")
public class Board extends BaseAggregateRoot<Board> {

    @Basic
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @OneToOne(mappedBy = "board", cascade = CascadeType.PERSIST)
    private BoardCountV2 boardCount;

    public Board withBoardCount() {

        boardCount = new BoardCountV2();
        boardCount.setBoard(this);
        return this;
    }
}