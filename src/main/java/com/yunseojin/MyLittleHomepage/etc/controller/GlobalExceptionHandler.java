package com.yunseojin.MyLittleHomepage.etc.controller;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BaseException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Throwable.class)
    public String errorHandler(Throwable e, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        BaseException baseException;
        //정의된 예외
        if (e instanceof BaseException) {
            baseException = (BaseException) e;
            ((BaseException) e).setErrorTrace(e.getStackTrace()[0].toString());
        }
        // validation 실패
        else if (e instanceof BindException) {
            baseException = new BaseException(e.getClass().getSimpleName(), ErrorMessage.VALIDATION_EXCEPTION);
            List<ObjectError> messageList = ((BindException) e).getBindingResult().getAllErrors();
            baseException.setErrorMessage(extractErrorMessage(messageList));
            baseException.setErrorTrace(e.getStackTrace()[0].toString());
        }
        //미정의 예외
        else {
            baseException = new BaseException(e.getClass().getSimpleName(), ErrorMessage.UNDEFINED_EXCEPTION);
            baseException.setErrorMessage(e.getMessage());
            baseException.setErrorTrace(e.getStackTrace()[0].toString());
        }
        redirectAttributes.addFlashAttribute("errorMessage", baseException.getErrorMessage());
        return "redirect:" + request.getHeader("Referer");
    }

    private String extractErrorMessage(List<ObjectError> messageList) {
        StringBuilder message = new StringBuilder();
        for (ObjectError objectError : messageList) {
            String validationMessage = objectError.getDefaultMessage();
            message.append(validationMessage).append("\n");
        }
        return message.toString();
    }
}
