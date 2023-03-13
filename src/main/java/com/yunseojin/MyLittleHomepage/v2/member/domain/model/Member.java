package com.yunseojin.MyLittleHomepage.v2.member.domain.model;

import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import com.yunseojin.MyLittleHomepage.v2.auth.domain.UserDetails;
import com.yunseojin.MyLittleHomepage.v2.auth.domain.UserRole;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.model.BaseAggregateRoot;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "UPDATE members SET is_deleted = 1 WHERE id=?")
@Table(name = "members")
public class Member extends BaseAggregateRoot<Member> implements UserDetails {

    @NotNull
    @Size(min = 6, max = 20, message = "ID는 6~20글자 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "ID는 알파벳과 숫자만 사용할 수 있습니다.")
    @Column(name = "username", nullable = false, length = 20)
    private String username;

    //    @NotNull
//    @Size(min = 8, max = 20, message = "비밀번호는 8~20글자 입니다.")
//    @Pattern(regexp = "[a-zA-Z0-9~!@#$%^&*()]+", message = "비밀번호는 알파벳, 숫자, 특수문자(~!@#$%^&*())만 사용할 수 있습니다.")
    @Column(name = "password", nullable = false)
    private String password;


    @NotNull
    @Size(min = 2, max = 12, message = "닉네임은 2~12글자 입니다")
    @Pattern(regexp = "[a-zA-Z0-9가-힣_]+", message = "닉네임은 알파벳, 한글, 숫자, _만 사용할 수 있습니다.")
    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    public void onCreated() {
        role = UserRole.NORMAL;
        password = PasswordUtil.getHashedPassword(password);
    }

    public void update(Member newMember) {

        if (newMember.getUsername() != null) {
            username = newMember.getUsername();
        }

        if (newMember.getNickname() != null) {
            nickname = newMember.getNickname();
        }

        if (newMember.getPassword() != null) {
            password = PasswordUtil.getHashedPassword(newMember.getPassword());
        }
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