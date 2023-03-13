package com.yunseojin.MyLittleHomepage.v2.contract.application.command;

import an.awesome.pipelinr.Command.Handler;

public interface CommandService<C extends Command<R>, R> extends Handler<C,R> {

}
