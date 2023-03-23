package com.yunseojin.MyLittleHomepage.domain.contract.command.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseErrorMessage implements ErrorMessage {

    INVALID_TOKEN_EXCEPTION("000001", "잘못된 인증 방식입니다.", HttpStatus.BAD_REQUEST),
    ;

    final String code;

    final String errorMessage;

    final HttpStatus httpStatus;

    BaseErrorMessage(String code, String errorMessage, HttpStatus httpStatus) {

        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
