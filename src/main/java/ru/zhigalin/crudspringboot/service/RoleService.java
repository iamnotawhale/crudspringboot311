package ru.zhigalin.crudspringboot.service;

import ru.zhigalin.crudspringboot.model.Role;

public interface RoleService {
    Role findRoleByName(String name);
}
