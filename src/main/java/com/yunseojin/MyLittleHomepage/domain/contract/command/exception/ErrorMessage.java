package com.yunseojin.MyLittleHomepage.domain.contract.command.exception;

import org.springframework.http.HttpStatus;

public interface ErrorMessage {

    String getCode();

    String getErrorMessage();

    HttpStatus getHttpStatus();
}
