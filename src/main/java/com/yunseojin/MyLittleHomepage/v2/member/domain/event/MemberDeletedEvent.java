package com.yunseojin.MyLittleHomepage.v2.member.domain.event;

import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;


public class MemberDeletedEvent extends MemberEvent {

    public MemberDeletedEvent(Member member) {
        super(member);
    }
}
