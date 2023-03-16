package com.yunseojin.MyLittleHomepage.v2.member.domain.model;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.validation.Create;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@Validated
@Value
public class MemberVo {

    @NotNull(groups = {Create.class})
    @Size(min = 6, max = 20, message = "ID는 6~20글자 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "ID는 알파벳과 숫자만 사용할 수 있습니다.")
    String username;

    @NotNull(groups = {Create.class})
    @Size(min = 8, max = 20, message = "비밀번호는 8~20글자 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9~!@#$%^&*()]+", message = "비밀번호는 알파벳, 숫자, 특수문자(~!@#$%^&*())만 사용할 수 있습니다.")
    String password;

    @NotNull(groups = {Create.class})
    @Size(min = 2, max = 12, message = "닉네임은 2~12글자 입니다")
    @Pattern(regexp = "[a-zA-Z0-9가-힣_]+", message = "닉네임은 알파벳, 한글, 숫자, _만 사용할 수 있습니다.")
    String nickname;
}