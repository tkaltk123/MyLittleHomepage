package com.yunseojin.MyLittleHomepage.v2.board.domain.query.entity;


import com.yunseojin.MyLittleHomepage.v2.contract.domain.query.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @Size(min = 2, max = 50, message = "게시판 이름은 2~50글자 입니다.")
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "board_id")
    private QueriedBoardCount boardCount;
}