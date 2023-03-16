package com.yunseojin.MyLittleHomepage.v2.member.domain.event;

import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;


public class MemberUpdatedEvent extends MemberEvent {

    public MemberUpdatedEvent(Member member) {
        super(member);
    }
}
