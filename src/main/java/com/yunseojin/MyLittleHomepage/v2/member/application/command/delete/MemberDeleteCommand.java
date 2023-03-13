package com.yunseojin.MyLittleHomepage.v2.member.application.command.delete;

import com.yunseojin.MyLittleHomepage.v2.contract.application.command.Command;
import com.yunseojin.MyLittleHomepage.v2.member.application.command.MemberAuthOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class MemberDeleteCommand extends MemberAuthOperation implements Command<Void> {

}
