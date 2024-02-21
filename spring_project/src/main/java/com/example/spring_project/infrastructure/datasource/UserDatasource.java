package com.example.spring_project.infrastructure.datasource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring_project.domain.model.user.NewUser;
import com.example.spring_project.domain.model.user.SearchUser;
import com.example.spring_project.domain.model.user.User;
import com.example.spring_project.domain.model.user.UserRepository;

@Repository
public class UserDatasource implements UserRepository{

    @Autowired
    private final UserMapper mapper;

    public UserDatasource(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<User> findAll() {
        return mapper.findAll();
    }
    @Override
    public User findById(Integer id) {
        return mapper.findById(id);
    }
    @Override
    public void update(User user) {
        int res = mapper.update(user);
        System.out.println("Updated "+ user.toString() +" : " +res);
    }

    @Override
    public void add(NewUser user) {
        int res = mapper.add(user);
        System.out.println("add user name["+ user.name() +"] res =" + res);
    }

    @Override
    public Integer delete(Integer id) {
        int res = mapper.delete(id);
        System.out.println("delete user id["+ id +"] res =" + res);
        return res;
    }
    @Override
    public List<User> findByCondition(SearchUser user) {
        return mapper.findByCondition(user);
    }

}