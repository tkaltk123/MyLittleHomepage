package com.yunseojin.MyLittleHomepage.v2.domain.contract.command.exception;

import org.springframework.http.HttpStatus;

public interface ErrorMessage {

    String getCode();

    String getErrorMessage();

    HttpStatus getHttpStatus();
}