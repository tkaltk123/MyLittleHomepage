package com.yunseojin.MyLittleHomepage.domain.contract.command.exception;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class BaseException extends RuntimeException {

    protected final String className;

    protected final String code;

    protected final HttpStatus httpStatus;

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());

        this.className = this.getClass().getSimpleName();
        this.code = errorMessage.getCode();
        this.httpStatus = errorMessage.getHttpStatus();
    }

    public BaseException(String className, ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());

        this.className = className;
        this.code = errorMessage.getCode();
        this.httpStatus = errorMessage.getHttpStatus();
    }

}