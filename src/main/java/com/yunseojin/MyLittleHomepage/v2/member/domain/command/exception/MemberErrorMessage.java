package com.yunseojin.MyLittleHomepage.v2.member.domain.command.exception;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.exception.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorMessage implements ErrorMessage {

    ALREADY_LOGIN_EXCEPTION("001001", "이미 로그인되어 있습니다.", HttpStatus.BAD_REQUEST),
    NOT_LOGIN_EXCEPTION("001002", "로그인 되지 않았습니다.", HttpStatus.BAD_REQUEST),
    LOGIN_ID_DUPLICATE_EXCEPTION("001003", "이미 등록된 ID 입니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_DUPLICATE_EXCEPTION("001004", "이미 등록된 닉네임 입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXISTS_EXCEPTION("001005", "존재하지 않는 회원입니다.", HttpStatus.BAD_REQUEST),
    INCORRECT_PASSWORD_EXCEPTION("001006", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    LOGIN_FAILED_EXCEPTION("001007", "ID 또는 패스워드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    ;

    final String code;

    final String errorMessage;

    final HttpStatus httpStatus;

    MemberErrorMessage(String code, String errorMessage, HttpStatus httpStatus) {

        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
