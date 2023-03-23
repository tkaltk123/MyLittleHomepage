package com.yunseojin.MyLittleHomepage.v2.domain.comment.command.exception;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.exception.BaseException;

public class CommentException extends BaseException {

    public CommentException(CommentErrorMessage errorMessage) {
        super(errorMessage);
    }
}
