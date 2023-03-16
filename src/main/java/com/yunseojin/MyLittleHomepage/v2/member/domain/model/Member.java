package com.yunseojin.MyLittleHomepage.v2.member.domain.model;

import auth.domain.UserDetails;
import auth.domain.UserRole;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.model.BaseAggregateRoot;
import com.yunseojin.MyLittleHomepage.v2.member.domain.event.MemberCreatedEvent;
import com.yunseojin.MyLittleHomepage.v2.member.domain.event.MemberDeletedEvent;
import com.yunseojin.MyLittleHomepage.v2.member.domain.event.MemberUpdatedEvent;
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
@SQLDelete(sql = "UPDATE members SET is_deleted = 1 WHERE id=?")
@Table(name = "members")
public class Member extends BaseAggregateRoot<Member> implements UserDetails {

    @Column(name = "username", nullable = false, length = 20)
    private String username;


    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    public Member(MemberVo memberVo) {
        this.username = memberVo.getUsername();
        this.password = PasswordUtil.getHashedPassword(memberVo.getPassword());
        this.nickname = memberVo.getNickname();
        this.role = UserRole.NORMAL;
        registerEvent(new MemberCreatedEvent(this));
    }

    public void update(MemberVo memberVo) {

        updateUsername(memberVo.getUsername());
        updateNickname(memberVo.getNickname());
        updatePassword(memberVo.getPassword());
        registerEvent(new MemberUpdatedEvent(this));
    }

    private void updateUsername(String username) {
        if (username != null) {
            this.username = username;
        }
    }

    private void updateNickname(String nickname) {
        if (nickname != null) {
            this.nickname = nickname;
        }
    }

    private void updatePassword(String password) {
        if (password != null) {
            this.password = PasswordUtil.getHashedPassword(password);
        }
    }

    @Override
    public void delete() {
        super.delete();
        registerEvent(new MemberDeletedEvent(this));
    }

    @Override
    public boolean isWrongPassword(String password) {
        return PasswordUtil.equals(password, this.password);
    }

    @Override
    public boolean isNotAdmin() {
        return role != UserRole.ADMIN;
    }
}