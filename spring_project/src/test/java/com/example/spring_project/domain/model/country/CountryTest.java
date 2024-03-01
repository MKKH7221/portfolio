package com.example.spring_project.domain.model.country;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@SpringBootTest
public class CountryTest {

    @Autowired
    Validator validator;

    /**
     * no error
     */
    @Test
    public void noError() {
        Country country = new Country("Italy","ITA");
        Set<ConstraintViolation<Country>> violations = validator.validate(country);
        assertThat(violations.size(), is(0));
    }

    @Test
    public void error_sizeTooLong() {
        Country country = new Country("Italy","IIIITA");
        Set<ConstraintViolation<Country>> violations = validator.validate(country);
        assertThat(violations.size(), is(1));
        assertEquals(Set.of(Country.LENGTH_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }

    @Test
    public void error_sizeTooShort() {
        Country country = new Country("Italy","TA");
        Set<ConstraintViolation<Country>> violations = validator.validate(country);
        assertThat(violations.size(), is(1));
        assertEquals(Set.of(Country.LENGTH_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }

    @Test
    public void error_empty() {
        Country country = new Country("","");
        Set<ConstraintViolation<Country>> violations = validator.validate(country);
        assertThat(violations.size(), is(2));
        assertEquals(Set.of(Country.NOT_EMPTY_ERROR, Country.LENGTH_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }



}
