package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.exception;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.command.exception.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommentErrorMessage implements ErrorMessage {

    NOT_WRITER_EXCEPTION("003001", "내가 작성한 댓글이 아닙니다.", HttpStatus.BAD_REQUEST),
    ;

    final String code;

    final String errorMessage;

    final HttpStatus httpStatus;

    CommentErrorMessage(String code, String errorMessage, HttpStatus httpStatus) {

        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
