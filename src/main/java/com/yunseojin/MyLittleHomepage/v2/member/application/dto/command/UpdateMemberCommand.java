package com.yunseojin.MyLittleHomepage.v2.member.application.dto.command;

import com.yunseojin.MyLittleHomepage.v2.member.application.dto.AuthOperation;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberCommand extends AuthOperation implements MemberCommand<MemberResponse> {

    private String username;

    private String password;

    private String nickname;

    private String currentPassword;
}
