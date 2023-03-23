package com.yunseojin.MyLittleHomepage.domain.post.command.exception;

import com.yunseojin.MyLittleHomepage.domain.contract.command.exception.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PostErrorMessage implements ErrorMessage {

    NOT_EXISTS_EXCEPTION("002001", "게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_WRITER_EXCEPTION("002002", "내가 작성한 게시글이 아닙니다.", HttpStatus.BAD_REQUEST),
    ;

    final String code;

    final String errorMessage;

    final HttpStatus httpStatus;

    PostErrorMessage(String code, String errorMessage, HttpStatus httpStatus) {

        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
