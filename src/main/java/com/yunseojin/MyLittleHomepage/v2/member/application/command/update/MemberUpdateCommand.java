package com.yunseojin.MyLittleHomepage.v2.member.application.command.update;

import com.yunseojin.MyLittleHomepage.v2.member.application.command.MemberAuthOperation;
import com.yunseojin.MyLittleHomepage.v2.member.application.command.MemberCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class MemberUpdateCommand extends MemberAuthOperation implements MemberCommand<String> {

    private String username;

    private String password;

    private String nickname;
}
