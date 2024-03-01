package com.example.spring_project.domain.model.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.example.spring_project.application.service.UserService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@SpringBootTest
public class IdTest {

    @Autowired
    Validator validator;
    @Autowired
    private UserService service;

    /**
     * no error
     */
    @Test
    @Sql("/test_insert_user_list.sql")
    public void noError() {

        User user = service.findAll().get(3);
        // Id id = new Id(1);
        Set<ConstraintViolation<Id>> violations = validator.validate(user.id());
        assertThat(violations.size(), is(0));
    }

    /**
     * length error
     */
    @Test
    public void error_length() {
        Id id = new Id(1000000);
        Set<ConstraintViolation<Id>> violations = validator.validate(id);
   
        assertThat(violations.size(), is(2));
        assertEquals(Set.of(Id.MAX_LENGTH_ERROR, Id.ID_CHECK_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }

    /**
     * not exists error
     */
    @Test
    public void error_notExists() {
        Id id = new Id(59);
        Set<ConstraintViolation<Id>> violations = validator.validate(id);
   
        assertThat(violations.size(), is(1));
        assertEquals(Set.of(Id.ID_CHECK_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }


}
