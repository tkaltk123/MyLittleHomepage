package com.yunseojin.MyLittleHomepage.v2.member.domain.exception;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.exception.BaseException;

public class MemberException extends BaseException {

    public MemberException(MemberErrorMessage errorMessage) {
        super(errorMessage);
    }
}
