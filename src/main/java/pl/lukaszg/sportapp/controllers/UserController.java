package pl.lukaszg.sportapp.controllers;

import org.springframework.web.bind.annotation.*;
import pl.lukaszg.sportapp.configurations.LoginCredentials;
import pl.lukaszg.sportapp.model.User;
import pl.lukaszg.sportapp.services.MailService;
import pl.lukaszg.sportapp.services.UserService;

import javax.mail.MessagingException;
import java.util.List;


@RestController
@RequestMapping("api/users")

public class UserController {


    private final UserService userService;
    private final MailService mailService;

    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping("/")
    public List<User> getAllUsers() throws MessagingException {
        mailService.sendMail("gorcek45@gmail.com","gorcek45@gmail.com","gorcek45@gmail.com",false);
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getAllUsers(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @PatchMapping("/{id}/changePassword")
    public String changePassword(@PathVariable("id") Long id, @RequestBody LoginCredentials credentials) {
        return userService.changePassword(id, credentials);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public String deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUserById(id);
    }

    @DeleteMapping(value = "/{userId}/teams/{teamId}", produces = "application/json")
    public String deleteUserFromTeamById(@PathVariable("userId") Long userId, @PathVariable("teamId") Long teamId) {
        return userService.deleteUserFromTeamById(userId, teamId);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public String updateProfile(@PathVariable("id") Long id, @RequestBody User user) {
        return userService.changeProfile(id, user);
    }

    @PutMapping(value = "/{userId}/teams/{teamId}", produces = "application/json")
    public String addUserToTeamById(@PathVariable("userId") Long userId, @PathVariable("teamId") Long teamId) {
        return userService.addUserToTeamById(userId, teamId);
    }

    @PutMapping(value = "/notification/{notificationId}", produces = "application/json")
    public String addUserToTeamByNotificationId(@PathVariable("notificationId") Long notificationId) {
        return userService.readNotification(notificationId);
    }


}

