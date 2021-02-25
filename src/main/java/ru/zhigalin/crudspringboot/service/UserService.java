package ru.zhigalin.crudspringboot.service;

import ru.zhigalin.crudspringboot.model.User;

public interface UserService {
    <S extends User> S save(S s);

    Iterable<User> findAll();

    void delete(User user);

    User findById(Long id);

    User findByLogin(String login);
}