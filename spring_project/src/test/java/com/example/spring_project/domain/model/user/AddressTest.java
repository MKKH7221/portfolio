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
public class AddressTest {

    @Autowired
    Validator validator;

    /**
     * no error
     */
    @Test
    public void noError() {
        Address address = new Address("30133-32/45 Venice, Metropolitan City of Venice");
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        assertThat(violations.size(), is(0));
    }

    @Test
    public void error_pattern_withSymbol() {
        Address address = new Address("30133-32/45 Venice, Metropolitan City of + Venice@");
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
   
        assertThat(violations.size(), is(1));
        assertEquals(Set.of(Address.PATTERN_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }
    @Test
    public void error_length() {
        Address address = new Address("30133-32/45 Venice, Metropolitan City of Venice, 30133-32/45 Venice, Metropolitan City of Venice, 30133-32/45 Venice");
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
   
        assertThat(violations.size(), is(1));
        assertEquals(Set.of(Address.LENGTH_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }
    @Test
    public void error_pattern_length() {
        Address address = new Address("30133-32/45 Venice!@+$#, Metropolitan City of Venice, 30133-32/45 Venice, Metropolitan City of Venice, 30133-32/45 Venice");
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
   
        assertThat(violations.size(), is(2));
        assertEquals(Set.of(Address.LENGTH_ERROR, Address.PATTERN_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }

    @Test
    public void error_notEmpty() {
        Address address = new Address("");
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
   
        assertThat(violations.size(), is(2));
        assertEquals(Set.of(Address.NOT_BLANK_ERROR, Address.PATTERN_ERROR),
                violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()));
    }
   


}
