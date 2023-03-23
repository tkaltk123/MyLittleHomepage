package com.yunseojin.MyLittleHomepage.v2.domain.board.command.exception;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.command.exception.BaseException;

public class BoardException extends BaseException {

    public BoardException(BoardErrorMessage errorMessage) {
        super(errorMessage);
    }
}
