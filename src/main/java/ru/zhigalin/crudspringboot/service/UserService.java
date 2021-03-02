package ru.zhigalin.crudspringboot.service;

import ru.zhigalin.crudspringboot.model.User;

public interface UserService {
    User save(User user);

    Iterable<User> findAll();

    void delete(User user);

    User findById(Long id);

    User findByLogin(String login);
}