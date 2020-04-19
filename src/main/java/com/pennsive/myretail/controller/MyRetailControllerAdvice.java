package com.pennsive.myretail.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class MyRetailControllerAdvice {	
    @ExceptionHandler({NoSuchElementException.class, RestClientException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(Exception e) {
    	return;
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleBadInput(Exception e) {
    	return;
    }
}
