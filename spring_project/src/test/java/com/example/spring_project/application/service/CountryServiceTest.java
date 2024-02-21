package com.example.spring_project.application.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.spring_project.domain.model.country.Country;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Test
    void getCountryList() {
        List<Country> actual = countryService.findAll();
        System.out.println(actual.size());
        int expected = 239;
        assertThat(expected, is(actual.size()));
    }
    @Test
    void test_findByCode() {
        String countryCode = "JPN";
        Country actual = countryService.findByCode(countryCode);
        String expected = "Japan"; 
        assertThat(expected, is(actual.name()));
    }
    @Test
    void test_findByName() {
        String countryName = "Japan";
        Country actual = countryService.findByName(countryName);
        String expected = "JPN"; 
        assertThat(expected, is(actual.code()));
    }

}
