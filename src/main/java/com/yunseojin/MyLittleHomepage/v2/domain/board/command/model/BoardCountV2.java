package com.yunseojin.MyLittleHomepage.v2.domain.board.command.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board_counts")
public class BoardCountV2 {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Basic
    @Column(name = "post_count", nullable = false)
    private Integer postCount = 0;

    public BoardCountV2(Board board) {
        this.board = board;
    }
}

