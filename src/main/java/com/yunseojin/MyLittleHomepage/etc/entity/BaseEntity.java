package com.yunseojin.MyLittleHomepage.etc.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    @CreatedDate
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false, updatable = false, insertable = false)
    @LastModifiedDate
    protected Date updatedAt;

    @Setter
    @Basic(optional = false)
    @Builder.Default
    @Column(name = "is_deleted")
    protected int isDeleted = 0;
}
