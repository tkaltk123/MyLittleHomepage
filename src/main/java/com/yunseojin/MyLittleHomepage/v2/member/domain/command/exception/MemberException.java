package com.yunseojin.MyLittleHomepage.v2.member.domain.command.exception;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.exception.BaseException;

public class MemberException extends BaseException {

    public MemberException(MemberErrorMessage errorMessage) {
        super(errorMessage);
    }
}
