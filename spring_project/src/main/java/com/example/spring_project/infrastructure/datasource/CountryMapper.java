package com.example.spring_project.infrastructure.datasource;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.example.spring_project.domain.model.country.Country;

@Mapper
public interface CountryMapper {

    List<Country> findAll();
    Country findByName(String countryName);
    Country findByCode(String countryCode);
}