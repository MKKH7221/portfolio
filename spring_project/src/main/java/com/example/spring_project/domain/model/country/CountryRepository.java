package com.example.spring_project.domain.model.country;

import java.util.List;

public interface CountryRepository {

    List<Country> findAll();
    Country findByName(String countryName);
    Country findByCode(String countryCode);
}
