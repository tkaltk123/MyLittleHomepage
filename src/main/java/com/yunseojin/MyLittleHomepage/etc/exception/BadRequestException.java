package com.yunseojin.MyLittleHomepage.etc.exception;


import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;

public class BadRequestException extends BaseException {

    public BadRequestException(String className, ErrorMessage errorMessage) {
        super(className, errorMessage);

    }

    public BadRequestException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
