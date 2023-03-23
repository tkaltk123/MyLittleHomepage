package com.yunseojin.MyLittleHomepage.domain.feedback.command.exception;

import com.yunseojin.MyLittleHomepage.domain.contract.command.exception.BaseException;

public class FeedbackException extends BaseException {

    public FeedbackException(FeedbackErrorMessage errorMessage) {
        super(errorMessage);
    }
}
