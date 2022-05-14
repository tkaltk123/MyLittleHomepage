package com.yunseojin.MyLittleHomepage.etc.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {
    UNDEFINED_EXCEPTION(0,"정의되지 않은 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    BIND_FAIL_EXCEPTION(1, "주소가 이미 사용중 입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_EXCEPTION(2,"유효하지 않은 값입니다.",HttpStatus.BAD_REQUEST),
    NULL_POINTER_EXCEPTION(3,"NULL 값이 참조되었습니다.",HttpStatus.BAD_REQUEST)
    ;

    Integer code;
    String errorMessage;
    HttpStatus httpStatus;

    ErrorMessage(int code, String errorMessage, HttpStatus httpStatus) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}