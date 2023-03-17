package com.yunseojin.MyLittleHomepage.v2.contract.domain.exception;

import org.springframework.http.HttpStatus;

public interface ErrorMessage {

    String getCode();

    String getErrorMessage();

    HttpStatus getHttpStatus();
}
