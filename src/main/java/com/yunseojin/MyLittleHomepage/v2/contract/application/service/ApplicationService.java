package com.yunseojin.MyLittleHomepage.v2.contract.application.service;


import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;

public interface ApplicationService {

    <R> R executeCommand(Command<R> command);

    <R> R executeQuery(Query<R> query);
}
