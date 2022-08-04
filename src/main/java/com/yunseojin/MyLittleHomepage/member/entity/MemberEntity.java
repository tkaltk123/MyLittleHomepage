package com.yunseojin.MyLittleHomepage.member.entity;

import com.yunseojin.MyLittleHomepage.etc.entity.BaseEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@SQLDelete(sql = "UPDATE members SET is_deleted = 1 WHERE id=?")
@Where(clause = "is_deleted = 0")
@Table(name = "members")
public class MemberEntity extends BaseEntity {

    private static class MemberEntityBuilderImpl extends MemberEntityBuilder<MemberEntity, MemberEntityBuilderImpl> {

        @Override
        public MemberEntityBuilderImpl password(String password) {

            if (password != null)
                super.password(PasswordUtil.getHashedPassword(password));
            else
                super.password(null);

            return self();
        }
    }

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

    public void update(MemberRequest memberRequest) {

        if (memberRequest.getLoginId() != null)
            loginId = memberRequest.getLoginId();

        if (memberRequest.getNickname() != null)
            nickname = memberRequest.getNickname();

        if (memberRequest.getPassword() != null)
            password = PasswordUtil.getHashedPassword(memberRequest.getPassword());
    }
}