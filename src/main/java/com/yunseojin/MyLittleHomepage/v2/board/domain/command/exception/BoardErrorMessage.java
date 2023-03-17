package com.yunseojin.MyLittleHomepage.v2.board.domain.command.exception;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.exception.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BoardErrorMessage implements ErrorMessage {

    NOT_EXISTS_EXCEPTION("002001", "존재하지 않는 게시판입니다.", HttpStatus.BAD_REQUEST),
    ;

    final String code;

    final String errorMessage;

    final HttpStatus httpStatus;

    BoardErrorMessage(String code, String errorMessage, HttpStatus httpStatus) {

        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
