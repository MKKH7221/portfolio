package com.example.spring_project.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_project.application.service.CountryService;
import com.example.spring_project.application.service.UserService;
import com.example.spring_project.domain.model.country.Country;
import com.example.spring_project.domain.model.user.NewUser;
import com.example.spring_project.domain.model.user.SearchUser;
import com.example.spring_project.domain.model.user.User;

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
    public List<User> search(@RequestParam (value="id", required = false) Integer id,
            @RequestParam (value="name", required = false) String name,
            @RequestParam (value="address", required = false) String address,
            @RequestParam (value="tel", required = false) String tel,
            @RequestParam (value="countryCode", required = false) String countryCode) {
    
        SearchUser condition = new SearchUser(id, name, address, tel, countryCode);

        List<User> resultList = userService.findByCondition(condition);
        for (User result : resultList) {
                System.out.println(result.toString());
        }
        return resultList;
    }

    @RequestMapping("/edit/{id}")
    public User detail(@PathVariable("id") Integer id) {
        User result = userService.findById(id);
        return result;
    }

    @PostMapping("/update")
    public User update(@RequestBody User user){
        System.out.println(user);
        userService.update(user);        
        User updatedUser = userService.findById(user.id());
        return updatedUser;
    }

    @PostMapping("/add")
    public void add(@RequestBody NewUser newUser){
        System.out.println(newUser.toString());
        userService.add(newUser);
    }
    
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(value="id") Integer id) {
        userService.delete(id);
    }
}