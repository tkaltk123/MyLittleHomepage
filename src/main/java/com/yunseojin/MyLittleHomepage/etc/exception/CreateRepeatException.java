package com.yunseojin.MyLittleHomepage.etc.exception;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"cause", "stackTrace", "message", "localizedMessage", "message", "suppressed"})
public class CreateRepeatException extends BaseException {

    protected int waitSeconds;

    public CreateRepeatException(int waitSeconds, String className, ErrorMessage errorMessage) {

        super(className, errorMessage);
        this.waitSeconds = waitSeconds;

    }

    public CreateRepeatException(int waitSeconds, ErrorMessage errorMessage) {

        super(errorMessage);
        this.waitSeconds = waitSeconds;
    }
}
