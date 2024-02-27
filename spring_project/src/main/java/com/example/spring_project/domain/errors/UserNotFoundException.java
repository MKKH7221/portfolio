package com.example.spring_project.domain.errors;

public class UserNotFoundException extends RuntimeException {

    private Integer id;

    public UserNotFoundException() {
        super();
    }
    

    public UserNotFoundException(Integer id) {
        super("No user by ID ["+ id +"]");
        this.id = id;
    }
    
}