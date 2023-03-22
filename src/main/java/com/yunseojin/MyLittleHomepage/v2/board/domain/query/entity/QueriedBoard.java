package com.yunseojin.MyLittleHomepage.v2.board.domain.query.entity;


import com.yunseojin.MyLittleHomepage.v2.contract.domain.query.entity.BaseEntity;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "boards")
public class QueriedBoard extends BaseEntity {

    @Basic
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @OneToOne(mappedBy = "board", cascade = CascadeType.PERSIST)
    private QueriedBoardCount boardCount;
}