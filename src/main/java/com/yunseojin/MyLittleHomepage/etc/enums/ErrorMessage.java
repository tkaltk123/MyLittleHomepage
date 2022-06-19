package com.yunseojin.MyLittleHomepage.etc.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {

    UNDEFINED_EXCEPTION(0,"정의되지 않은 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR)
    ,BIND_FAIL_EXCEPTION(1, "주소가 이미 사용중 입니다.", HttpStatus.INTERNAL_SERVER_ERROR)
    ,VALIDATION_EXCEPTION(2,"유효하지 않은 값입니다.",HttpStatus.BAD_REQUEST)
    ,NULL_POINTER_EXCEPTION(3,"NULL 값이 참조되었습니다.",HttpStatus.BAD_REQUEST)
    ,ALREADY_LOGIN_EXCEPTION(4,"이미 로그인되어 있습니다.",HttpStatus.BAD_REQUEST)
    ,LOGIN_ID_DUPLICATE_EXCEPTION(5,"이미 등록된 ID 입니다.",HttpStatus.BAD_REQUEST)
    ,NICKNAME_DUPLICATE_EXCEPTION(6,"이미 등록된 닉네임 입니다.",HttpStatus.BAD_REQUEST)
    ,NOT_LOGIN_EXCEPTION(7,"로그인 되지 않았습니다.",HttpStatus.BAD_REQUEST)
    ,NOT_EXISTS_MEMBER_EXCEPTION(8,"존재하지 않는 회원입니다.",HttpStatus.BAD_REQUEST)
    ,NOT_EXISTS_BOARD_EXCEPTION(9,"존재하지 않는 게시판입니다.",HttpStatus.BAD_REQUEST)
    ,NOT_EXISTS_POST_EXCEPTION(10,"존재하지 않는 게시글입니다.",HttpStatus.BAD_REQUEST)
    ,NOT_EXISTS_COMMENT_EXCEPTION(11,"존재하지 않는 댓글입니다.",HttpStatus.BAD_REQUEST)
    ,INCORRECT_PASSWORD_EXCEPTION(12,"비밀번호가 일치하지 않습니다.",HttpStatus.BAD_REQUEST)
    , NOT_WRITER_EXCEPTION(13,"작성한 사용자가 다른 게시글입니다.",HttpStatus.BAD_REQUEST)
    ,PAGE_OUT_OF_RANGE_EXCEPTION(14,"페이지 범위를 벗어났습니다.",HttpStatus.BAD_REQUEST)
    ,COMMENT_PARENT_EXCEPTION(15,"대댓글에 대댓글을 작성할 수 없습니다.",HttpStatus.BAD_REQUEST)
    ,POST_REPEAT_EXCEPTION(16,"10초 내에 게시글을 두번 작성했습니다.",HttpStatus.BAD_REQUEST)
    ,COMMENT_REPEAT_EXCEPTION(17,"10초 내에 댓글을 두번 작성했습니다.",HttpStatus.BAD_REQUEST)
    ,LOGIN_FAILED_EXCEPTION(18,"ID 또는 패스워드가 일치하지 않습니다.",HttpStatus.BAD_REQUEST)
    ;

    Integer code;
    String errorMessage;
    HttpStatus httpStatus;

    ErrorMessage(int code, String errorMessage, HttpStatus httpStatus) {

        this.code = code;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}