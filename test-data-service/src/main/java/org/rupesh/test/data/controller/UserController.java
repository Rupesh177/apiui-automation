package org.rupesh.test.data.controller;

import org.rupesh.test.data.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.rupesh.test.data.model.User;

import java.sql.SQLException;

@RestController
@RequestMapping("/testdata/user")
public class UserController {

    @Autowired
    private UserDataService service;

    @PostMapping
    public User create() throws SQLException {
        return service.createUser();
    }

    @GetMapping
    public User get() throws SQLException {
        return service.getUser();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) throws SQLException {
        service.deleteUser(id);
    }
}