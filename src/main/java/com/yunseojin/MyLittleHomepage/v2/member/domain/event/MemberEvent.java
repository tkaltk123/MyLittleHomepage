package com.yunseojin.MyLittleHomepage.v2.member.domain.event;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class MemberEvent implements DomainEvent {

    protected final Object payload;

    protected MemberEvent(Object payload) {
        this.payload = payload;
    }
}
