package com.example.spring_project.domain.model.user;
import java.util.List;

public interface UserRepository {

    User findById(Integer id);
    void update(User user);
    void add(NewUser user);
    Integer delete (Integer id);
    List<User> findAll();
    List<User> findByCondition(SearchUser user);

}