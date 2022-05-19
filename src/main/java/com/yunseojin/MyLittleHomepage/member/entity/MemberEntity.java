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
@SQLDelete(sql = "UPDATE MEMBERS SET IS_DELETED = 1 WHERE ID=?")
@Where(clause = "IS_DELETED = 0")
@Table(name = "MEMBERS")
public class MemberEntity extends BaseEntity {
    @Basic
    @Column(name = "LOGIN_ID", nullable = false, length = 20)
    private String loginId;

    @Basic
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    @Basic
    @Column(name = "NICKNAME", nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEMBER_TYPE", nullable = false)
    private MemberType memberType;
}