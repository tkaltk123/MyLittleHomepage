package com.yunseojin.MyLittleHomepage.v2.member.application.dto.command;

import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberAuthOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class DeleteMemberCommand extends MemberAuthOperation implements Command<Void> {

}
