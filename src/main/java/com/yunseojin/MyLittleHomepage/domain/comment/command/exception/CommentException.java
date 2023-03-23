package com.yunseojin.MyLittleHomepage.domain.comment.command.exception;

import com.yunseojin.MyLittleHomepage.domain.contract.command.exception.BaseException;

public class CommentException extends BaseException {

    public CommentException(CommentErrorMessage errorMessage) {
        super(errorMessage);
    }
}
