package com.worldpay.task.controller;

import com.worldpay.task.exception.DoesNotExistException;
import com.worldpay.task.exception.InvalidDateException;
import com.worldpay.task.exception.OfferExpiredException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlingController extends BaseController {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class, InvalidDateException.class,
        OfferExpiredException.class})
    @ResponseBody
    public ResponseEntity<String> global(HttpServletRequest httpServletRequest, Exception exception) {
        return badRequest(exception.getMessage());
    }

    @ExceptionHandler(value = {DoesNotExistException.class})
    @ResponseBody
    public ResponseEntity<String> notFound(HttpServletRequest httpServletRequest, Exception exception) {
        return notFound(exception.getMessage());
    }
}
