package com.example.spring_project.domain.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Tel(

    @NotBlank (message = NOT_BLANK_ERROR)
    @Size(max=10, message = SIZE_ERROR)
    @Pattern(regexp = "^[0-9]+$", message = PATTERN_ERROR)
    String value ) {

    public static final String SIZE_ERROR= "Tel must be no longer than 10 disit";
    public static final String PATTERN_ERROR= "Tel must be disit";
    public static final String NOT_BLANK_ERROR = "Tel must be not empty";


}   