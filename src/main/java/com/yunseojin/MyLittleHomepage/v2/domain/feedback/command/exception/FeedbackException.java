package com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.exception;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.exception.BaseException;

public class FeedbackException extends BaseException {

    public FeedbackException(FeedbackErrorMessage errorMessage) {
        super(errorMessage);
    }
}
