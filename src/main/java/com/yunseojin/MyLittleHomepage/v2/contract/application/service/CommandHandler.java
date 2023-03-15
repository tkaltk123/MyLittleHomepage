package com.yunseojin.MyLittleHomepage.v2.contract.application.service;

import an.awesome.pipelinr.Command.Handler;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;

public interface CommandHandler<C extends Command<R>, R> extends Handler<C, R> {

}
