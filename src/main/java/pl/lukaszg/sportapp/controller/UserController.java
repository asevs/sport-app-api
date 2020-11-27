package pl.lukaszg.sportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.lukaszg.sportapp.configuration.LoginCredentials;
import pl.lukaszg.sportapp.model.User;
import pl.lukaszg.sportapp.service.UserService;

import java.util.List;

@RestController("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<User> getAllUsers() {
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

    @PutMapping(value = "/{userId}/teams/{teamId}", produces = "application/json")
    public String deleteUserFromTeamById(@PathVariable("userId") Long userId, @PathVariable("teamId") Long teamId){
        return userService.deleteUserFromTeamById(userId, teamId);
    }


    @PutMapping(value = "/{userId}/teams/{teamId}", produces = "application/json")
    public String addUserToTeamById(@PathVariable("userId") Long userId, @PathVariable("teamId") Long teamId){
        return userService.addUserToTeamById(userId, teamId);
    }

    @PutMapping(value = "/{userId}/rooms/{roomId}", produces = "application/json")
    public String addUserToRoomById(@PathVariable("userId") Long userId, @PathVariable("roomId") Long roomId){
        return userService.addUserToRoomById(userId, roomId);
    }

    @PutMapping(value = "/{userId}/rooms/{roomId}", produces = "application/json")
    public String deleteUserFromRoomById(@PathVariable("userId") Long userId, @PathVariable("roomId") Long roomId){
        return userService.deleteUserFromRoomById(userId, roomId);
    }


}


