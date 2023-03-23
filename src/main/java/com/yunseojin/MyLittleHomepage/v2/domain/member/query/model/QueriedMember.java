package com.yunseojin.MyLittleHomepage.v2.domain.member.query.model;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.validation.Create;
import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.validation.Update;
import com.yunseojin.MyLittleHomepage.v2.domain.contract.query.model.BaseEntity;
import com.yunseojin.MyLittleHomepage.v2.domain.member.vo.MemberAuthority;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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


    @NotNull
    @Size(min = 6, max = 20, message = "ID는 6~20글자 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "ID는 알파벳과 숫자만 사용할 수 있습니다.")
    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @NotNull(groups = {Create.class})
    @Size(min = 8, max = 20, groups = {Create.class, Update.class},
            message = "비밀번호는 8~20글자 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9~!@#$%^&*()]+", groups = {Create.class, Update.class},
            message = "비밀번호는 알파벳, 숫자, 특수문자(~!@#$%^&*())만 사용할 수 있습니다.")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Size(min = 2, max = 12, message = "닉네임은 2~12글자 입니다")
    @Pattern(regexp = "[a-zA-Z0-9가-힣_]+", message = "닉네임은 알파벳, 한글, 숫자, _만 사용할 수 있습니다.")
    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberAuthority role;

    public boolean isWrongPassword(String password) {
        return password == null || BCrypt.checkpw(password, this.password);
    }
}
