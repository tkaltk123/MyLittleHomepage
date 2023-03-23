package com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.event;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class FeedbackEvent implements DomainEvent {

    protected final Object payload;

    protected FeedbackEvent(Object payload) {
        this.payload = payload;
    }
}
