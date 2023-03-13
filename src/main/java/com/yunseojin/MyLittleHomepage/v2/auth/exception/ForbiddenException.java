package com.yunseojin.MyLittleHomepage.v2.auth.exception;

public class ForbiddenException extends AuthenticationException {

    public ForbiddenException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
