package com.yunseojin.MyLittleHomepage.v2.member.application.dto.command;

import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberAuthOperation;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberCommand extends MemberAuthOperation implements MemberCommand<String> {

    private String username;

    private String password;

    private String nickname;
}
