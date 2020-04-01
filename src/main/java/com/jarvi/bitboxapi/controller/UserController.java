package com.jarvi.bitboxapi.controller;

import com.jarvi.bitboxapi.controller.security.IsAdmin;
import com.jarvi.bitboxapi.model.UserDTO;
import com.jarvi.bitboxapi.persistence.entity.User;
import com.jarvi.bitboxapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @IsAdmin
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> find(@PathVariable(name = "id") Integer id) throws Exception {
        return ResponseEntity.ok(userService.findOne(id));
    }

    @IsAdmin
    @GetMapping(value = "/users")
    public ResponseEntity<Iterable<User>> findAll() throws Exception {
        return ResponseEntity.ok(userService.findAll());
    }

    @IsAdmin
    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody UserDTO user) throws Exception {
        userService.save(user);
        return ResponseEntity.ok(userService.findAll());
    }

    @IsAdmin
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) throws Exception {
        userService.delete(id);
        return ResponseEntity.ok(userService.findAll());
    }

}
