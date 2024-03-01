package com.example.spring_project.domain.model.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record Address(

    @NotBlank (message=NOT_BLANK_ERROR)
    @Length(max=100, message = LENGTH_ERROR)
    @Pattern(regexp = "^[a-zA-Z0-9 ,-\\/]+$", message = PATTERN_ERROR)
    String value
) {

    public static final String LENGTH_ERROR = "Address must be no longer than 100 characters";
    public static final String PATTERN_ERROR =  "Address must be used alphabet, number, space, comma, hyphen, slash";
    public static final String NOT_BLANK_ERROR = "Adderss must be not null or empty or blank";

} 
