package ru.zhigalin.crudspringboot.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import ru.zhigalin.crudspringboot.dto.UserDto;
import ru.zhigalin.crudspringboot.model.Role;
import ru.zhigalin.crudspringboot.model.User;
import ru.zhigalin.crudspringboot.service.RoleService;

import java.util.stream.Collectors;

public class EntityDtoTransfer {

    private final RoleService roleService;

    @Autowired
    public EntityDtoTransfer(RoleService roleService) {
        this.roleService = roleService;
    }

    public User fromDto(UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles() == null ? null : userDto.getRoles().stream()
                .map(roleService::findRoleByName)
                .collect(Collectors.toSet())
        );
        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles() == null ? null : user.getRoles().stream()
                .map(Role::getLogin)
                .collect(Collectors.toSet()));
        return userDto;
    }
}
