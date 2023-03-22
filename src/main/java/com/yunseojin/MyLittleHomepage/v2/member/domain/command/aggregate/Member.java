package com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.aggregate.BaseAggregateRoot;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.event.MemberCreatedEvent;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.event.MemberDeletedEvent;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.event.MemberUpdatedEvent;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import com.yunseojin.MyLittleHomepage.v2.member.domain.vo.MemberAuthority;
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
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCrypt;

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

    protected Member(QueriedMember memberInfo) {
        this.username = memberInfo.getUsername();
        setPassword(memberInfo.getPassword());
        this.nickname = memberInfo.getNickname();
        this.role = MemberAuthority.NORMAL;
        registerEvent(new MemberCreatedEvent(readOnly()));
    }

    private void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    protected Member update(QueriedMember memberInfo) {

        updateUsername(memberInfo.getUsername());
        updateNickname(memberInfo.getNickname());
        updatePassword(memberInfo.getPassword());
        registerEvent(new MemberUpdatedEvent(readOnly()));
        return this;
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
            setPassword(password);
        }
    }

    @Override
    protected void delete() {
        super.delete();
        registerEvent(new MemberDeletedEvent(readOnly()));
    }

    public QueriedMember readOnly() {
        return MemberConverter.INSTANCE.readOnly(this);
    }

    @Mapper
    interface MemberConverter {

        MemberConverter INSTANCE = Mappers.getMapper(MemberConverter.class);

        QueriedMember readOnly(Member member);
    }
}