package com.yunseojin.MyLittleHomepage.v2.domain.post.command.exception;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.exception.BaseException;

public class PostException extends BaseException {

    public PostException(PostErrorMessage errorMessage) {
        super(errorMessage);
    }
}
