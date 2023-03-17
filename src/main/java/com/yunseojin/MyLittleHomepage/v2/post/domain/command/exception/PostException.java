package com.yunseojin.MyLittleHomepage.v2.post.domain.command.exception;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.exception.BaseException;

public class PostException extends BaseException {

    public PostException(PostErrorMessage errorMessage) {
        super(errorMessage);
    }
}
