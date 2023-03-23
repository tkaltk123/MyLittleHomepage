package com.yunseojin.MyLittleHomepage.application.member.dto.command;

import com.yunseojin.MyLittleHomepage.application.member.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.application.member.dto.MemberCommand;
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
