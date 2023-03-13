package com.yunseojin.MyLittleHomepage.v2.contract.handler;


import com.yunseojin.MyLittleHomepage.v2.contract.application.command.Command;
import com.yunseojin.MyLittleHomepage.v2.contract.application.query.Query;

public interface ApplicationService {

    <R> R executeCommand(Command<R> command);

    <R> R executeQuery(Query<R> query);
}
