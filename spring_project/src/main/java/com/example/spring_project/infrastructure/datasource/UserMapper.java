package com.example.spring_project.infrastructure.datasource;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.example.spring_project.domain.model.user.NewUser;
import com.example.spring_project.domain.model.user.SearchUser;
import com.example.spring_project.domain.model.user.User;

@Mapper
public interface UserMapper {

    List<User> findAll();
    User findById(Integer id);
    Integer update(User user);
    Integer add(NewUser newUser);
    Integer delete(Integer id);
    List<User> findByCondition(SearchUser user);
}