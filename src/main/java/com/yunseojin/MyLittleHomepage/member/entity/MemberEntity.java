package com.yunseojin.MyLittleHomepage.member.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
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
@SQLDelete(sql = "UPDATE members SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted = 0")
@Table(name = "members")
public class MemberEntity extends BaseEntity {
    @Basic
    @Column(name = "login_id", nullable = false, length = 20)
    private String loginId;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", nullable = false)
    private MemberType memberType;
}