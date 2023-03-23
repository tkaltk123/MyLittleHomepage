package com.yunseojin.MyLittleHomepage.v2.domain.member.command.exception;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.exception.BaseException;

public class MemberException extends BaseException {

    public MemberException(MemberErrorMessage errorMessage) {
        super(errorMessage);
    }
}
