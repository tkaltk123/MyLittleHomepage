package com.yunseojin.MyLittleHomepage.v2.member.domain.event;

import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;


public class MemberCreatedEvent extends MemberEvent {

    public MemberCreatedEvent(Member member) {
        super(member);
    }
}
