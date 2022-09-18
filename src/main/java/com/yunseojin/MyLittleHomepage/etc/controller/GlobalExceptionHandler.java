package com.yunseojin.MyLittleHomepage.etc.controller;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Order(1)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public String errorHandler(Throwable e, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        log.error(e.getMessage(), e);
        if (e instanceof BindException) {

            var be = (BindException) e;
            var errors = be.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray();

            redirectAttributes.addFlashAttribute("invalidParams", errors);
            redirectAttributes.addFlashAttribute("errorMessage", "");
        } else if (e instanceof BaseException) {

            var ex = (BaseException) e;

            if (ex.getCode().equals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode()))
                return "redirect:/login";

            redirectAttributes.addFlashAttribute("errorMessage", (ex.getErrorMessage()));
        } else
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }
}
