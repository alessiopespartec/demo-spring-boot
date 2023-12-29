package com.example.demo.controllers;

import com.example.demo.exceptions.EmptyOrNullFieldException;
import com.example.demo.response.ResponseHandler;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(EmptyOrNullFieldException.class)
    public ResponseEntity<Object> handleEmptyOrNullFieldException(EmptyOrNullFieldException e) {
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String typeName = (e.getRequiredType() != null) ? e.getRequiredType().getSimpleName().toLowerCase() : "unknown type";
        String error = "Invalid parameter: " + e.getName() + " should be of type " + typeName;
        return ResponseHandler.generateResponse(error, HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e) {
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}
