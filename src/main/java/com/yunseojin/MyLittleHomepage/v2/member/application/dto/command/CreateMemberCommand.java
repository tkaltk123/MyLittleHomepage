package com.yunseojin.MyLittleHomepage.v2.member.application.dto.command;

import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberCommand implements MemberCommand<MemberResponse> {

    private String username;

    private String password;

    private String nickname;
}
