package com.yunseojin.MyLittleHomepage.v2.board.domain.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "BOARD_COUNTS")
public class BoardCountV2 {

    @Id
    private Long id;

    @Setter
    @MapsId
    @OneToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Basic
    @Setter
    @Column(name = "post_count", nullable = false)
    private Integer postCount = 0;
}