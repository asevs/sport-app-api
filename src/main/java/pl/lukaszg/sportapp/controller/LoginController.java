package pl.lukaszg.sportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lukaszg.sportapp.configuration.LoginCredentials;
import pl.lukaszg.sportapp.model.User;
import pl.lukaszg.sportapp.service.UserService;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials credentials) {
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

}
