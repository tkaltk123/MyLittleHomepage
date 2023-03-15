package com.yunseojin.MyLittleHomepage.v2.member.application.dto.command;

import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberAuthOperation;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class UpdateMemberCommand extends MemberAuthOperation implements MemberCommand<String> {

    private String username;

    private String password;

    private String nickname;
}
