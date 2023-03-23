package com.yunseojin.MyLittleHomepage.domain.board.command.event;

import com.yunseojin.MyLittleHomepage.domain.contract.command.event.DomainEvent;
import lombok.Getter;

@Getter
public abstract class BoardEvent implements DomainEvent {

    protected final Object payload;

    protected BoardEvent(Object payload) {
        this.payload = payload;
    }
}
