package com.study.repository;

import com.study.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private Map<String, User> users = new HashMap<>();

    @Override
    public Set<User> findAll() {
        return new HashSet<>(users.values());
    }

    @Override
    public User findByLogin(String login) {
        return users.get(login);
    }

    @Override
    public void save(User user) {
        users.put(user.getLogin(), user);
    }
}
