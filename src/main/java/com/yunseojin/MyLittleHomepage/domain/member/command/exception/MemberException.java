package com.yunseojin.MyLittleHomepage.domain.member.command.exception;

import com.yunseojin.MyLittleHomepage.domain.contract.command.exception.BaseException;

public class MemberException extends BaseException {

    public MemberException(MemberErrorMessage errorMessage) {
        super(errorMessage);
    }
}
