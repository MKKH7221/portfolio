package com.example.spring_project.domain.errors;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHundler {

    // 403
    @ExceptionHandler({AccessDeniedException.class})  
    @ResponseStatus(HttpStatus.FORBIDDEN)  
    public ErrorResponse handleException(AccessDeniedException e) {
        return new ErrorResponse("notAuthorized", "The request was not authorized.");
    }

    // custom error
    @ExceptionHandler({UserNotFoundException.class})  
    @ResponseStatus(HttpStatus.NOT_FOUND) 
    public ErrorResponse handleEmployeeNotFoundException(UserNotFoundException e) {
        return new ErrorResponse("userNotFound", "The user was not found.");
    }

    // 500
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse("systemError", e.getMessage());
    }

}