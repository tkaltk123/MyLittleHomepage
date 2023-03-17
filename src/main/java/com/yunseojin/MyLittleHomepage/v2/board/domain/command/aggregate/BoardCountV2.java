package com.yunseojin.MyLittleHomepage.v2.board.domain.command.aggregate;

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
@NoArgsConstructor
@Entity
@Table(name = "BOARD_COUNTS")
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
}