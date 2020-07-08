package com.hsbc.twitter.exception;

import com.hsbc.twitter.dto.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }

        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, errors);
        return handleExceptionInternal(ex, apiException, headers, apiException.getStatus(), request);
    }

    @ExceptionHandler(value = {UserDoesNotExist.class})
    protected ResponseEntity<Object> handleUserDoesNotExist(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, Collections.singletonList(bodyOfResponse));
        return handleExceptionInternal(ex, apiException,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
