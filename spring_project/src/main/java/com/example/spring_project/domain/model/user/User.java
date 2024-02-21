package com.example.spring_project.domain.model.user;

import com.example.spring_project.domain.model.country.Country;


public record User (
    Integer id, 
    String name, 
    String address, 
    String tel, 
    Country country) {
}

