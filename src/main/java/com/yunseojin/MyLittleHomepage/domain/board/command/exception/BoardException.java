package com.yunseojin.MyLittleHomepage.domain.board.command.exception;

import com.yunseojin.MyLittleHomepage.domain.contract.command.exception.BaseException;

public class BoardException extends BaseException {

    public BoardException(BoardErrorMessage errorMessage) {
        super(errorMessage);
    }
}
