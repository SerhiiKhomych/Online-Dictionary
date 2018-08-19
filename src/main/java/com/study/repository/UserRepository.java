package com.study.repository;

import com.study.pojo.User;

import java.util.Set;

public interface UserRepository {
    Set<User> findAll();
    User findByUsername(String username);
    void save(User user);
}
