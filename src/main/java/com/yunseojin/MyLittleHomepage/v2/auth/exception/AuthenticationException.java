package com.yunseojin.MyLittleHomepage.v2.auth.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
    }
}
