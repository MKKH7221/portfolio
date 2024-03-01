package com.example.spring_project.domain.model.country;

import org.hibernate.validator.constraints.Length;

import com.example.spring_project.application.validation.CountryCodeCheck;

import jakarta.validation.constraints.NotEmpty;

public record Country (
    
    String name, 
    
    @NotEmpty (message = NOT_EMPTY_ERROR)
    @Length (min=3, max=3, message = LENGTH_ERROR)
    @CountryCodeCheck (message = NOT_EXISTS_ERROR)
    String code
) 
{
    public static final String NOT_EMPTY_ERROR = "Country must be selected";
    public static final String LENGTH_ERROR = "Country must be 3 characters";
    public static final String NOT_EXISTS_ERROR = "Country code is not exists";

}
