package com.yunseojin.MyLittleHomepage.v2.domain.comment.command.event;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class CommentEvent implements DomainEvent {

    protected final Object payload;

    protected CommentEvent(Object payload) {
        this.payload = payload;
    }
}
