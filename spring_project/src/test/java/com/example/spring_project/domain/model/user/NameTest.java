package com.example.spring_project.domain.model.user;

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

public class NameTest {

    @Autowired
    Validator validator;

    /**
     * no error
     */
    @Test
    public void noError() {
        Name name = new Name("New User Name");
        Set<ConstraintViolation<Name>> violations = validator.validate(name);
        assertThat(violations.size(), is(0));
    }

    @Test
    public void error_length() {
        Name name = new Name("User Name More Than twenty Characters");
        Set<ConstraintViolation<Name>> violations = validator.validate(name);

        assertThat(violations.size(), is(1));
        assertEquals(Set.of(Name.SIZE_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));

    }
    @Test
    public void error_pattern() {
        Name name = new Name("New% User@ Name!");
        Set<ConstraintViolation<Name>> violations = validator.validate(name);
        assertThat(violations.size(), is(1));
        assertEquals(Set.of(Name.PATTERN_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }

    @Test
    public void error_length_pattern() {
        Name name = new Name("New% User@ Name! Alphabet Space)");
        Set<ConstraintViolation<Name>> violations = validator.validate(name);
        assertThat(violations.size(), is(2));
        assertEquals(Set.of(Name.SIZE_ERROR, Name.PATTERN_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }

    @Test
    public void error_notEmpty() {
        Name name = new Name("");
        Set<ConstraintViolation<Name>> violations = validator.validate(name);
        assertThat(violations.size(), is(2));
        assertEquals(Set.of(Name.NOT_BLANK_ERROR, Name.PATTERN_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }

}
