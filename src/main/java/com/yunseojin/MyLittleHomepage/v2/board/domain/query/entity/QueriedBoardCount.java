package com.yunseojin.MyLittleHomepage.v2.board.domain.query.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "BOARD_COUNTS")
public class QueriedBoardCount {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "board_id")
    private QueriedBoard board;

    @Basic
    @Column(name = "post_count", nullable = false)
    private Integer postCount;
}