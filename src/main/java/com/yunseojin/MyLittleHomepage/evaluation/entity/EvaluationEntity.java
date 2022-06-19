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
    private MemberEntity writer;
}