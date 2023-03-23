package com.yunseojin.MyLittleHomepage.v2.application.contract.service;

import an.awesome.pipelinr.Command.Handler;
import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Command;

public interface CommandHandler<C extends Command<R>, R> extends Handler<C, R> {

}
