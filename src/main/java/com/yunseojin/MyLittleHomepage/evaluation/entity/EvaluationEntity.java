package com.yunseojin.MyLittleHomepage.evaluation.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.EvaluationType;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Where(clause = "IS_DELETED = 0")
@Table(name = "EVALUATIONS")
@DiscriminatorColumn(name = "DTYPE")
public abstract class EvaluationEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "EVALUATION_TYPE", nullable = false)
    protected EvaluationType evaluationType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private MemberEntity writer;
}