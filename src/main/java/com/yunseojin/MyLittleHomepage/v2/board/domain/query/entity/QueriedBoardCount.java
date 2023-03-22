package com.yunseojin.MyLittleHomepage.v2.board.domain.query.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "board_counts")
public class QueriedBoardCount {

    @Id
    @Column(name = "board_id")
    private Long id;

    @Basic
    @Column(name = "post_count", nullable = false)
    private Integer postCount;
}