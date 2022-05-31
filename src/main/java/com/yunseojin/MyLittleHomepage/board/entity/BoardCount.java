package com.yunseojin.MyLittleHomepage.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "BOARD_COUNTS")
public class BoardCount {
    @Id
    private Long id;

    @MapsId
    @OneToOne(optional = false)
    private BoardEntity board;

    @Basic
    @Setter
    @Column(name = "POST_COUNT", nullable = false)
    private Integer postCount = 0;
}