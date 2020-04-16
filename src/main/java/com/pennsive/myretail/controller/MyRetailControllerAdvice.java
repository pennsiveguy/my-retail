package com.pennsive.myretail.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyRetailControllerAdvice {
	protected static final String NOT_FOUND_MESSAGE = "Item not found. Try Amazon.";
	
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(Exception e) {
    	return;
    }

    @ExceptionHandler({NumberFormatException.class, HttpMessageNotReadableException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleBadNumericInput(Exception e) {
    	return;
    }
}
