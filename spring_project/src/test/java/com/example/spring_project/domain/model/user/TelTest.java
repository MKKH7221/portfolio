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
public class TelTest {

    @Autowired
    Validator validator;

    /**
     * no error
     */
    @Test
    public void noError() {
        Tel tel = new Tel("3423423423");
        Set<ConstraintViolation<Tel>> violations = validator.validate(tel);
        assertThat(violations.size(), is(0));
    }

    /**
     * length error
     */
    @Test
    public void error_length() {
        Tel tel = new Tel("342344857394573");
        Set<ConstraintViolation<Tel>> violations = validator.validate(tel);
   
        assertThat(violations.size(), is(1));
        assertEquals(Set.of(Tel.SIZE_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }

    @Test
    public void error_pattern_notNurmeric() {
        Tel tel = new Tel("aaaaaa");
        Set<ConstraintViolation<Tel>> violations = validator.validate(tel);
   
        assertThat(violations.size(), is(1));
        assertEquals(Set.of(Tel.PATTERN_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }
    @Test
    public void error_pattern_withSymbol() {
        Tel tel = new Tel("31394-3333");
        Set<ConstraintViolation<Tel>> violations = validator.validate(tel);
   
        assertThat(violations.size(), is(1));
        assertEquals(Set.of(Tel.PATTERN_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }
    @Test
    public void error_pattern_length() {
        Tel tel = new Tel("31394-3333-12039");
        Set<ConstraintViolation<Tel>> violations = validator.validate(tel);
   
        assertThat(violations.size(), is(2));
        assertEquals(Set.of(Tel.PATTERN_ERROR, Tel.SIZE_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }

    @Test
    public void error_notEmpty() {
        Tel tel = new Tel("");
        Set<ConstraintViolation<Tel>> violations = validator.validate(tel);
   
        assertThat(violations.size(), is(2));
        assertEquals(Set.of(Tel.NOT_BLANK_ERROR, Tel.PATTERN_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }



}
