package com.example.spring_project.domain.model.user;

import com.example.spring_project.application.validation.IdCheck;

import jakarta.validation.constraints.Max;

public record Id(

    // @NotNull (message=NOT_EMPTY_ERROR)
    @Max(value = 999999, message = MAX_LENGTH_ERROR)
    @IdCheck (message = ID_CHECK_ERROR)
    Integer value
    
) { 

    // public final static String NOT_EMPTY_ERROR = "Id may not be null";
    public final static String MAX_LENGTH_ERROR= "Id must be from 1 to 999999";
    public final static String ID_CHECK_ERROR = "Id does not exists";
    
} 