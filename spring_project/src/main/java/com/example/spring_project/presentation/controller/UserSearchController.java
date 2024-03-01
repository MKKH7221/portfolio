package com.example.spring_project.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_project.application.service.CountryService;
import com.example.spring_project.application.service.UserService;
import com.example.spring_project.application.validation.IdCheck;
import com.example.spring_project.domain.model.country.Country;
import com.example.spring_project.domain.model.user.Id;
import com.example.spring_project.domain.model.user.NewUser;
import com.example.spring_project.domain.model.user.SearchUser;
import com.example.spring_project.domain.model.user.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;

@Validated
@RestController
@CrossOrigin(origins = {"http://localhost:9000"})
public class UserSearchController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private CountryService countryService;

    @RequestMapping("/init")
    public List<Country> init() {
        List<Country> countryList = countryService.findAll();
        System.out.println(countryList.size());
        return countryList;
    }

    @RequestMapping("/search") 
    public List<User> search(
            @RequestParam (value="id", required = false) Integer id,
            @RequestParam (value="name", required = false) String name,
            @RequestParam (value="address", required = false) String address,
            @RequestParam (value="tel", required = false) String tel,
            @RequestParam (value="countryCode", required = false) String countryCode) {
    
        SearchUser condition = new SearchUser(id, name, address, tel, countryCode);
        return userService.findByCondition(condition);
    }

    @GetMapping("/edit/{id}")
    public User detail (@PathVariable("id")
            @Max( value = 9999, message=Id.MAX_LENGTH_ERROR)
            @IdCheck (message = Id.ID_CHECK_ERROR)
            Integer id) {
        return userService.findById(id);
    }

    // @PostMapping("/update")
    @PatchMapping("/update")
    public User update(@Validated @RequestBody User user){
        return  userService.update(user);  
    }

    @PostMapping("/add")
    public void add(@Validated @RequestBody NewUser newUser){
        userService.add(newUser);
    }
    
    @DeleteMapping("/delete")
    public void delete(@Valid @RequestBody Id id) {
        userService.delete(id.value());
    }

}