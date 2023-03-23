package com.yunseojin.MyLittleHomepage.v2.domain.member.command.event;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class MemberEvent implements DomainEvent {

    protected final Object payload;

    protected MemberEvent(Object payload) {
        this.payload = payload;
    }
}
