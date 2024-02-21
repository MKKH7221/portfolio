package com.example.spring_project.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_project.domain.model.country.Country;
import com.example.spring_project.domain.model.country.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    public List<Country> findAll () {
        return repository.findAll(); 
    }

    public Country findByCode (String countryCode) {
        return repository.findByCode(countryCode);
    }

    public Country findByName (String countryName) {
        return repository.findByName(countryName);
    }
    
}
