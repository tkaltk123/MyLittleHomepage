package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.exception;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.exception.BaseException;

public class CommentException extends BaseException {

    public CommentException(CommentErrorMessage errorMessage) {
        super(errorMessage);
    }
}
