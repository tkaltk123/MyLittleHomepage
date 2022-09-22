package com.yunseojin.MyLittleHomepage.board.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Basic
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @OneToOne(mappedBy = "board", cascade = CascadeType.PERSIST)
    private BoardCount boardCount;

    public BoardEntity withBoardCount() {

        boardCount = new BoardCount();
        boardCount.setBoard(this);
        return this;
    }
}