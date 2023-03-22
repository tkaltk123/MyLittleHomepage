package com.yunseojin.MyLittleHomepage.v2.post.domain.command.event;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class PostEvent implements DomainEvent {

    protected final Object payload;

    protected PostEvent(Object payload) {
        this.payload = payload;
    }
}