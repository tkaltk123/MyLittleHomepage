package com.yunseojin.MyLittleHomepage.application.contract.service;

import an.awesome.pipelinr.Command.Handler;
import com.yunseojin.MyLittleHomepage.application.contract.dto.Command;

public interface CommandHandler<C extends Command<R>, R> extends Handler<C, R> {

}
