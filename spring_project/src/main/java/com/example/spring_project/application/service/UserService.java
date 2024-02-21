package com.example.spring_project.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_project.domain.model.user.NewUser;
import com.example.spring_project.domain.model.user.SearchUser;
import com.example.spring_project.domain.model.user.User;
import com.example.spring_project.domain.model.user.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll () {
        return repository.findAll(); 
    }
    public List<User> findByCondition (SearchUser user) {
        return repository.findByCondition(user); 
    }

    public User findById (Integer id) {
        return repository.findById(id); 
    }
    public void update (User user) {
        repository.update(user);
    }
    public void add (NewUser user) {
        repository.add(user);
    }
    public void delete (Integer id) {
        repository.delete(id);
    }

}