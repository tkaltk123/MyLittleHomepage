package com.yunseojin.MyLittleHomepage.evaluation.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.EvaluationType;
import com.yunseojin.MyLittleHomepage.v2.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "evaluations")
@DiscriminatorColumn(name = "dtype")
public abstract class EvaluationEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation_type", nullable = false)
    protected EvaluationType evaluationType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;
}