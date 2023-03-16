package com.yunseojin.MyLittleHomepage.v2.member.domain.event;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.event.DomainEvent;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import lombok.Getter;

@Getter
public abstract class MemberEvent implements DomainEvent<Member> {

    protected final Member aggregate;

    protected MemberEvent(Member member) {
        this.aggregate = member;
    }
}
