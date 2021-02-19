package ru.zhigalin.crudspringboot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.zhigalin.crudspringboot.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}