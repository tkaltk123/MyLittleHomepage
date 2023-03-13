package com.yunseojin.MyLittleHomepage.v2.member.application.command;

import com.yunseojin.MyLittleHomepage.v2.contract.application.command.Command;


public interface MemberCommand<A> extends Command<A> {

    String getUsername();

    String getPassword();

    String getNickname();
}
