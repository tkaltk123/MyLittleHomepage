package com.yunseojin.MyLittleHomepage.etc.exception;


import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;

public class CreateRepeatException extends BaseException {
    protected int waitSeconds;

    public CreateRepeatException(int waitSeconds, String className, ErrorMessage errorMessage) {
        super(className, errorMessage);
        this.waitSeconds = waitSeconds;

    }

    public CreateRepeatException(int waitSeconds, ErrorMessage errorMessage) {
        super(errorMessage);
        this.waitSeconds = waitSeconds;
    }
}
