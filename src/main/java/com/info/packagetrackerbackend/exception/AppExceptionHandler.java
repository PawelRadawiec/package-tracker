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

import java.util.*;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @NotNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) {
        List<List<String>> response = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> appendResponse(error, response));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private void appendResponse(ObjectError error, List<List<String>> response) {
        response.add(Arrays.asList(((FieldError) error).getField(), error.getDefaultMessage()));
    }

}
