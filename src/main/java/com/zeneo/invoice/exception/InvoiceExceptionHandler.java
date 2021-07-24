package com.zeneo.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class InvoiceExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvoiceNotFoundException.class)
    public Map<String, String> handleNotFound() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Invoice is not found");
        return map;
    }

}
