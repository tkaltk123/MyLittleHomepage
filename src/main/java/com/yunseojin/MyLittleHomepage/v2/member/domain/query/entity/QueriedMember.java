package com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity;

import auth.domain.UserDetails;
import auth.domain.UserRole;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "")
@Table(name = "members")
public class QueriedMember extends BaseEntity implements UserDetails {

    @Column(name = "username", nullable = false, length = 20)
    private String username;


    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Override
    public boolean isWrongPassword(String password) {
        return !PasswordUtil.equals(password, this.password);
    }

    @Override
    public boolean isNotAdmin() {
        return role != UserRole.ADMIN;
    }
}
