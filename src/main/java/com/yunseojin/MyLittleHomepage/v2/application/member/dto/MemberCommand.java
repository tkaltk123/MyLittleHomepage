package com.yunseojin.MyLittleHomepage.v2.application.member.dto;

import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Command;


public interface MemberCommand<A> extends Command<A> {

    String getUsername();

    String getPassword();

    String getNickname();
}
