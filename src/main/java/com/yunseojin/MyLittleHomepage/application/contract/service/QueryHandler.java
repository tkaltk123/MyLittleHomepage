package com.yunseojin.MyLittleHomepage.application.contract.service;

import an.awesome.pipelinr.Command.Handler;
import com.yunseojin.MyLittleHomepage.application.contract.dto.Query;

public interface QueryHandler<C extends Query<R>, R> extends Handler<C, R> {

}
