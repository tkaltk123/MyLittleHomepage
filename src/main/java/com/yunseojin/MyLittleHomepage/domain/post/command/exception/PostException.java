package com.yunseojin.MyLittleHomepage.domain.post.command.exception;

import com.yunseojin.MyLittleHomepage.domain.contract.command.exception.BaseException;

public class PostException extends BaseException {

    public PostException(PostErrorMessage errorMessage) {
        super(errorMessage);
    }
}
