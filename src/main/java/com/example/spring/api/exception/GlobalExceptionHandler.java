package com.example.spring.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> errorHandler(MethodArgumentNotValidException ex, WebRequest wr){
        List<String> errors =ex.getBindingResult().getFieldErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> errorHandler(CustomerNotFoundException ex, WebRequest wr){
        String errors =ex.getLocalizedMessage();
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
