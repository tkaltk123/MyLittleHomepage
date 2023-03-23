package com.yunseojin.MyLittleHomepage.application.member.dto;

import com.yunseojin.MyLittleHomepage.application.contract.dto.Command;


public interface MemberCommand<A> extends Command<A> {

    String getUsername();

    String getPassword();

    String getNickname();
}
