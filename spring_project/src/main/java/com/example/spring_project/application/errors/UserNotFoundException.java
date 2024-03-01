package com.example.spring_project.application.errors;

public class UserNotFoundException extends RuntimeException {


    public UserNotFoundException() {
        super();
    }
    

    public UserNotFoundException(Integer id) {
        super("No user found by ID ["+ id +"]");
    }
    
}