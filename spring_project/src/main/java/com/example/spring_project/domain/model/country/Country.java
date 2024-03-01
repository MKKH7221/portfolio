package com.example.spring_project.domain.model.country;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;

public record Country (
    
    String name, 
    
    /**
     * [TODO] Consider to make custom Validation to check the code in DB
     */
    @NotEmpty (message = NOT_EMPTY_ERROR)
    @Length (min=3, max=3, message = LENGTH_ERROR)
    String code
) 
{
    public static final String NOT_EMPTY_ERROR = "Country must be selected";
    public static final String LENGTH_ERROR = "Country must be 3 characters";

}
