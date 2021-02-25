package ru.zhigalin.crudspringboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zhigalin.crudspringboot.model.Role;
import ru.zhigalin.crudspringboot.model.User;
import ru.zhigalin.crudspringboot.model.UserDto;
import ru.zhigalin.crudspringboot.service.RoleService;
import ru.zhigalin.crudspringboot.service.UserService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }


    @GetMapping
    public String allUsers(Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        model.addAttribute("currentUser", userDto);
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") UserDto userDto) {
        return "redirect:/admin";
    }

    @PostMapping()
    public String add(@ModelAttribute("user") UserDto userDto) {
        User user = fromDto(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        UserDto userDto = toDto(user);
        model.addAttribute("user", userDto);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("user") UserDto userDto) {
        User user = fromDto(userDto);
        user.setId(id);
        user.setPassword(userDto.getPassword());
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(userService.findById(id));
        return "redirect:/admin";
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
