package auth.exception;

import lombok.Getter;

public enum ErrorMessage {
    INVALID_USERNAME_OR_PASSWORD("유저이름 또는 비밀번호가 올바르지 않습니다."),
    NOT_BEARER_TOKEN("잘못된 인증 방식입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    NOT_ADMIN("접근 권한이 없는 사용자입니다.");


    @Getter
    final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}