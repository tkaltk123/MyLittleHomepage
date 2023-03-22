package com.yunseojin.MyLittleHomepage.v2.board.domain.command.event;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class BoardEvent implements DomainEvent {

    protected final Object payload;

    protected BoardEvent(Object payload) {
        this.payload = payload;
    }
}
