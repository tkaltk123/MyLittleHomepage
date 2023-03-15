package com.yunseojin.MyLittleHomepage.v2.member.application.dto.command;

import com.yunseojin.MyLittleHomepage.v2.auth.dto.Token;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberCommand implements MemberCommand<Token> {

    private String username;

    private String password;

    private String nickname;
}
