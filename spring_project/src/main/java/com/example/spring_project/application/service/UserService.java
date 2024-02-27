package com.example.spring_project.application.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_project.domain.errors.UserNotFoundException;
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
        User user = repository.findById(id);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException(id);
        }
        return user;
    }
        
    public User update (User user) {
        if (Objects.isNull(repository.findById(user.id()))) {
            throw new UserNotFoundException(user.id());
        }
        repository.update(user);
        return repository.findById(user.id());
    }
    public void add (NewUser user) {
        repository.add(user);
    }
    public void delete (Integer id) {
        if (Objects.isNull(repository.findById(id))) {
            throw new UserNotFoundException(id);
        }
        repository.delete(id);
    }

}