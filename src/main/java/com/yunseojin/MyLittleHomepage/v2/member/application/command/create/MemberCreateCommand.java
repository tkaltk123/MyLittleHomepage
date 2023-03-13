package com.yunseojin.MyLittleHomepage.v2.member.application.command.create;

import com.yunseojin.MyLittleHomepage.v2.auth.dto.Token;
import com.yunseojin.MyLittleHomepage.v2.member.application.command.MemberCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateCommand implements MemberCommand<Token> {

    private String username;

    private String password;

    private String nickname;
}
