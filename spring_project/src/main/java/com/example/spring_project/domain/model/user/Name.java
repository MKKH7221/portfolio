package com.example.spring_project.domain.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Name(
    
    @NotBlank(message = NOT_BLANK_ERROR)
    @Size(max=20, message = SIZE_ERROR)
    @Pattern(regexp = "^[a-zA-Z ]+$", message = PATTERN_ERROR)
    String value

) { 

    public static final String SIZE_ERROR= "Name must be no longer than 20 characters";
    public static final String PATTERN_ERROR = "Name must be used alphabet or space";
    public static final String NOT_BLANK_ERROR = "Name must be not null or empty or blank";

} 