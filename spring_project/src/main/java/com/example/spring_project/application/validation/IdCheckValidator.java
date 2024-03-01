package com.example.spring_project.application.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.example.spring_project.domain.model.user.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdCheckValidator implements ConstraintValidator<IdCheck, Integer> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(IdCheck IdCheck) {
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {

        return !ObjectUtils.isEmpty(repository.findById(id));
    }
}