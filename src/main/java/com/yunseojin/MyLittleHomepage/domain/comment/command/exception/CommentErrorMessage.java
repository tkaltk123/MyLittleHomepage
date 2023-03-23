package com.yunseojin.MyLittleHomepage.domain.comment.command.exception;

import com.yunseojin.MyLittleHomepage.domain.contract.command.exception.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommentErrorMessage implements ErrorMessage {

    NOT_EXISTS_EXCEPTION("003001", "댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_WRITER_EXCEPTION("003002", "내가 작성한 댓글이 아닙니다.", HttpStatus.BAD_REQUEST),
    PARENT_NOT_EXISTS_EXCEPTION("003003", "답글을 작성할 댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
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
