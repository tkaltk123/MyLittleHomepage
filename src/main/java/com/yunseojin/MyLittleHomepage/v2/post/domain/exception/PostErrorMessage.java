package com.yunseojin.MyLittleHomepage.v2.post.domain.exception;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.exception.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PostErrorMessage implements ErrorMessage {

    NOT_WRITER_EXCEPTION("002001", "내가 작성한 게시글이 아닙니다.", HttpStatus.BAD_REQUEST),
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
