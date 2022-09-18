package com.yunseojin.MyLittleHomepage.etc.controller;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Order(0)
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class RestGlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> errorHandler(Throwable e) {
        BaseException baseException;
        //정의된 예외
        if (e instanceof BaseException) {
            baseException = (BaseException) e;
            ((BaseException) e).setErrorTrace(e.getStackTrace()[0].toString());
        }
        // validation 실패
        else if (e instanceof MethodArgumentNotValidException) {
            baseException = new BaseException(e.getClass().getSimpleName(), ErrorMessage.VALIDATION_EXCEPTION);
            List<ObjectError> messageList = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
            baseException.setErrorMessage(extractErrorMessage(messageList));
            baseException.setErrorTrace(e.getStackTrace()[0].toString());
        }
        //서버 시작 실패
        else if (e instanceof BindException) {
            baseException = new BaseException(e.getClass().getSimpleName(), ErrorMessage.BIND_FAIL_EXCEPTION);
            List<ObjectError> messageList = ((BindException) e).getAllErrors();
            baseException.setErrorMessage(extractErrorMessage(messageList));
            baseException.setErrorTrace(e.getStackTrace()[0].toString());
        }
        //미정의 예외
        else {
            baseException = new BaseException(e.getClass().getSimpleName(), ErrorMessage.UNDEFINED_EXCEPTION);
            baseException.setErrorMessage(e.getMessage());
            baseException.setErrorTrace(e.getStackTrace()[0].toString());
        }

        log.error(baseException.getErrorMessage(), e);

        return new ResponseEntity<>(baseException, baseException.getHttpStatus());
    }

    private String extractErrorMessage(List<ObjectError> messageList) {
        StringBuilder message = new StringBuilder();
        for (ObjectError objectError : messageList) {
            String validationMessage = objectError.getDefaultMessage();
            message.append("[").append(validationMessage).append("]");
        }
        return message.toString();
    }
}
