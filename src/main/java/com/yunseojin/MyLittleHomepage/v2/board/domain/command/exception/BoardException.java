package com.yunseojin.MyLittleHomepage.v2.board.domain.command.exception;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.exception.BaseException;

public class BoardException extends BaseException {

    public BoardException(BoardErrorMessage errorMessage) {
        super(errorMessage);
    }
}
