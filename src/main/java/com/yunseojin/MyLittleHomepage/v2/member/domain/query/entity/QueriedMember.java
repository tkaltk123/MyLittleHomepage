package com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.query.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.v2.member.domain.vo.MemberAuthority;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "members")
public class QueriedMember extends BaseEntity {

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberAuthority role;

    public boolean isWrongPassword(String password) {
        return password == null || BCrypt.checkpw(password, this.password);
    }
}
