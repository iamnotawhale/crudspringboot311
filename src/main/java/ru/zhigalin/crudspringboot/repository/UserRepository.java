package ru.zhigalin.crudspringboot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.zhigalin.crudspringboot.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
}
