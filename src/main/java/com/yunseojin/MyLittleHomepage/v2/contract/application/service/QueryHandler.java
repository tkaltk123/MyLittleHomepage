package com.yunseojin.MyLittleHomepage.v2.contract.application.service;

import an.awesome.pipelinr.Command.Handler;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;

public interface QueryHandler<C extends Query<R>, R> extends Handler<C, R> {

}
