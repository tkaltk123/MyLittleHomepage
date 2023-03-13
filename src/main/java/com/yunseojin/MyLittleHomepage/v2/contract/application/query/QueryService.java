package com.yunseojin.MyLittleHomepage.v2.contract.application.query;

import an.awesome.pipelinr.Command.Handler;

public interface QueryService<C extends Query<R>, R> extends Handler<C,R> {

}
