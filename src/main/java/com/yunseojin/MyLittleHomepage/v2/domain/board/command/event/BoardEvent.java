package com.yunseojin.MyLittleHomepage.v2.domain.board.command.event;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class BoardEvent implements DomainEvent {

    protected final Object payload;

    protected BoardEvent(Object payload) {
        this.payload = payload;
    }
}
