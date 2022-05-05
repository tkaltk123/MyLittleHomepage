package com.yunseojin.MyLittleHomepage.hashtag.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@SQLDelete(sql = "UPDATE HASHTAGS SET IS_DELETED = 1 WHERE ID=?")
@Where(clause = "IS_DELETED = 0")
@Table(name = "HASHTAGS")
public class HashtagEntity extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "POST_ID", nullable = false)
    private PostEntity post;

    @Column(name = "TAG", nullable = false)
    private String tag;
}