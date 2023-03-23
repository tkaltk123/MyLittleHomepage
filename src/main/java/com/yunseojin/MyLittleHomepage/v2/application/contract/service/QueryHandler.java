package com.yunseojin.MyLittleHomepage.v2.application.contract.service;

import an.awesome.pipelinr.Command.Handler;
import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Query;

public interface QueryHandler<C extends Query<R>, R> extends Handler<C, R> {

}
