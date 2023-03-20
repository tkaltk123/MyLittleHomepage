package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.event;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class CommentEvent implements DomainEvent {

    protected final Object payload;

    protected CommentEvent(Object payload) {
        this.payload = payload;
    }
}
