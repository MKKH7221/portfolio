package com.example.spring_project.application.validation;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = IdCheckValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdCheck {

    String message() default "Id does not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
}
