package com.yunseojin.MyLittleHomepage.v2.member.application.dto;

import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;


public interface MemberCommand<A> extends Command<A> {

    String getUsername();

    String getPassword();

    String getNickname();
}
