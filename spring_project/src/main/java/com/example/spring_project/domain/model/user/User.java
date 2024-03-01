package com.example.spring_project.domain.model.user;

import com.example.spring_project.domain.model.country.Country;

import jakarta.validation.Valid;


public record User ( 
    
    @Valid
    Id id,

    @Valid 
    Name name,
    
    @Valid 
    Address address,
    
    @Valid 
    Tel tel,
    
    @Valid 
    Country country) {
}

