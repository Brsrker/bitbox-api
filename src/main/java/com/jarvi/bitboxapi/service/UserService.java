package com.jarvi.bitboxapi.service;

import com.jarvi.bitboxapi.controller.exception.NotFoundException;
import com.jarvi.bitboxapi.model.UserDTO;
import com.jarvi.bitboxapi.persistence.entity.Role;
import com.jarvi.bitboxapi.persistence.entity.User;
import com.jarvi.bitboxapi.persistence.repository.RoleRepository;
import com.jarvi.bitboxapi.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public User save(UserDTO user) {
        User usernameExist = userRepository.findByUsername(user.getUsername());
        if (usernameExist != null){
            return null;
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER");
        roles.add(role);
        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        return userRepository.findById(id);
    }

    public boolean delete(int id) {
        User user = userRepository.findById(id);
        if (user == null){
            throw new NotFoundException("The User " + id + " not exist!");
        }

        if (user.getRoles() != null) {
            for (Role role: user.getRoles()){
                role.removeUser(user);
                roleRepository.save(role);
            }
        }
        user.getRoles().clear();

        userRepository.delete(user);
        return true;
    }
}
