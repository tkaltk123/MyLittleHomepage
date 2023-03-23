package com.yunseojin.MyLittleHomepage.domain.post.command.event;

import com.yunseojin.MyLittleHomepage.domain.contract.command.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class PostEvent implements DomainEvent {

    protected final Object payload;

    protected PostEvent(Object payload) {
        this.payload = payload;
    }
}
