package com.yunseojin.MyLittleHomepage.v2.contract.domain.event;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.model.BaseAggregateRoot;

public interface DomainEvent<A extends BaseAggregateRoot<A>> {

    A getAggregate();
}
