package com.yunseojin.MyLittleHomepage.domain.member.command.event;

import com.yunseojin.MyLittleHomepage.domain.contract.command.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class MemberEvent implements DomainEvent {

    protected final Object payload;

    protected MemberEvent(Object payload) {
        this.payload = payload;
    }
}
