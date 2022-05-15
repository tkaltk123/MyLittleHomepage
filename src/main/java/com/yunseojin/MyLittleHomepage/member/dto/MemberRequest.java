package com.yunseojin.MyLittleHomepage.member.dto;

import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest {
    @Size(min = 6, max = 20, groups = {
            ValidationGroups.SignUp.class,
            ValidationGroups.Update.class,
            ValidationGroups.LogIn.class
    }, message = "ID는 6~20글자 입니다")
    @Pattern(regexp = "[a-zA-Z0-9]+", groups = {
            ValidationGroups.SignUp.class,
            ValidationGroups.Update.class,
            ValidationGroups.LogIn.class
    }, message = "ID는 알파벳과 숫자만 사용할 수 있습니다.")
    @NotNull(groups = {
            ValidationGroups.SignUp.class,
            ValidationGroups.LogIn.class
    }, message = "ID가 비어있습니다.")
    private String loginId;

    @Size(min = 8, max = 20, groups = {
            ValidationGroups.SignUp.class,
            ValidationGroups.Update.class,
            ValidationGroups.Delete.class,
            ValidationGroups.LogIn.class
    }, message = "비밀번호는 8~20글자 입니다")
    @Pattern(regexp = "[a-zA-Z0-9~!@#$%^&*()]+", groups = {
            ValidationGroups.SignUp.class,
            ValidationGroups.Update.class,
            ValidationGroups.Delete.class,
            ValidationGroups.LogIn.class
    }, message = "비밀번호는 알파벳과 숫자, 일부 특수기호만 사용할 수 있습니다.")
    @NotNull(groups = {
            ValidationGroups.SignUp.class,
            ValidationGroups.Delete.class,
            ValidationGroups.LogIn.class
    }, message = "비밀번호가 비어있습니다.")
    private String password;

    @Size(min = 2, max = 12, groups = {
            ValidationGroups.SignUp.class,
            ValidationGroups.Update.class
    }, message = "닉네임은 2~12글자 입니다")
    @Pattern(regexp = "[a-zA-Z0-9가-힣_]+", groups = {
            ValidationGroups.SignUp.class,
            ValidationGroups.Update.class
    }, message = "닉네임은 알파벳, 한글, 숫자, _만 사용할 수 있습니다.")
    @NotNull(groups = {
            ValidationGroups.SignUp.class
    }, message = "닉네임이 비어있습니다.")
    private String nickname;
}
