package pl.lukaszg.sportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.lukaszg.sportapp.configuration.LoginCredentials;
import pl.lukaszg.sportapp.model.User;
import pl.lukaszg.sportapp.service.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public User getAllUsers(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials credentials) {
    }

    @PatchMapping("/user/changePassword/{id}")
    public String changePassword(@PathVariable("id") Long id, @RequestBody LoginCredentials credentials) {
        return userService.changePassword(id, credentials);
    }

    @PostMapping(value = "/registers", consumes = "application/json", produces = "application/json")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

}


