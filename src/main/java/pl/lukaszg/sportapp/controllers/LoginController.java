package pl.lukaszg.sportapp.controllers;

import org.springframework.web.bind.annotation.*;
import pl.lukaszg.sportapp.configurations.LoginCredentials;
import pl.lukaszg.sportapp.model.User;
import pl.lukaszg.sportapp.services.UserService;
@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials credentials) {
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

}
