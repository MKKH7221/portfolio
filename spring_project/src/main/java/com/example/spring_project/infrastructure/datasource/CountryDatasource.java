package com.example.spring_project.infrastructure.datasource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring_project.domain.model.country.Country;
import com.example.spring_project.domain.model.country.CountryRepository;

@Repository
public class CountryDatasource implements CountryRepository{

    @Autowired
    private final CountryMapper mapper;

    public CountryDatasource(CountryMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Country> findAll() {
        return mapper.findAll();
    }

    @Override
    public Country findByCode(String countryCode) {
        return mapper.findByCode(countryCode);
    }

    @Override
    public Country findByName(String countryName) {
        
        return mapper.findByName(countryName);
    }


}