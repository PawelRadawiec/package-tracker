package com.info.packagetrackerbackend.exception;

import com.sun.istack.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @NotNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) {
        Map<String, String> invalidFields = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> createError(error, invalidFields));
        return new ResponseEntity<>(invalidFields, HttpStatus.BAD_REQUEST);
    }

    private void createError(ObjectError error, Map<String, String> invalidFields) {
        invalidFields.put(((FieldError) error).getField(), error.getDefaultMessage());
    }

}
