package ru.zhigalin.crudspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zhigalin.crudspringboot.repository.RoleRepository;
import ru.zhigalin.crudspringboot.repository.UserRepository;
import ru.zhigalin.crudspringboot.model.Role;
import ru.zhigalin.crudspringboot.model.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, RoleService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Transactional
    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public <S extends User> S save(S user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }


    @Transactional
    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }

    @Transactional
    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }


}