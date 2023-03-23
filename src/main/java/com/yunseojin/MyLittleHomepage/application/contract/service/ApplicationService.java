package com.yunseojin.MyLittleHomepage.application.contract.service;


import com.yunseojin.MyLittleHomepage.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.application.contract.dto.Query;

public interface ApplicationService {

    <R> R executeCommand(Command<R> command);

    <R> R executeQuery(Query<R> query);
}
