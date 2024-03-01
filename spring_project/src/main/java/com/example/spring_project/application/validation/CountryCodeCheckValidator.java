package com.example.spring_project.application.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.example.spring_project.domain.model.country.CountryRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryCodeCheckValidator implements ConstraintValidator<CountryCodeCheck, String> {

    @Autowired
    private CountryRepository repository;

    @Override
    public void initialize(CountryCodeCheck countryCodeCheck) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !ObjectUtils.isEmpty(repository.findByCode(value));
    }
}