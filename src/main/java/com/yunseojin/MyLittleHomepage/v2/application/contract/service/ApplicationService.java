package com.yunseojin.MyLittleHomepage.v2.application.contract.service;


import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Query;

public interface ApplicationService {

    <R> R executeCommand(Command<R> command);

    <R> R executeQuery(Query<R> query);
}
