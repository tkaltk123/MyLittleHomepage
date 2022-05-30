package com.yunseojin.MyLittleHomepage.board.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
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
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE BOARDS SET IS_DELETED = 1 WHERE ID=?")
@Where(clause = "IS_DELETED = 0")
@Table(name = "BOARDS")
public class BoardEntity extends BaseEntity {
    @Basic
    @Setter
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Builder.Default
    @OneToOne(mappedBy = "board", fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
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