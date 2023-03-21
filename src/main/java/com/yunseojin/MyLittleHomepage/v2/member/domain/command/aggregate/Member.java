package com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate;

import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.aggregate.BaseAggregateRoot;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.event.MemberCreatedEvent;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.event.MemberDeletedEvent;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.event.MemberUpdatedEvent;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.vo.MemberAuthority;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.vo.MemberVo;
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
public class Member extends BaseAggregateRoot<Member> {

    @Column(name = "username", nullable = false, length = 20)
    private String username;


    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberAuthority role;

    protected Member(MemberVo memberVo) {
        this.username = memberVo.getUsername();
        this.password = PasswordUtil.getHashedPassword(memberVo.getPassword());
        this.nickname = memberVo.getNickname();
        this.role = MemberAuthority.NORMAL;
        registerEvent(new MemberCreatedEvent(this));
    }

    protected void update(MemberVo memberVo) {

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
    protected void delete() {
        super.delete();
        registerEvent(new MemberDeletedEvent(this));
    }

    public boolean isWrongPassword(String password) {
        return !PasswordUtil.equals(password, this.password);
    }
}