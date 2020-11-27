package pl.lukaszg.sportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.configuration.LoginCredentials;
import pl.lukaszg.sportapp.model.*;
import pl.lukaszg.sportapp.service.exceptions.ItemNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamService teamService;
    @Autowired
    RoomService roomService;
    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public List<User> findAll() {
        return userRepository.findAll();
    }

    //Sprawdzamy czy user znajduje się w bazie. Jeżeli nie to tworzymy usera i zwracamy done. Jeżeli istnieje to zwracamy busy
    public String registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername())) {
            String password = user.getPassword();
            user.setPassword(bcrypt.encode(password));
            user.setCreatedUserDate(LocalDateTime.now());
            user.setLoginType(UserLoginType.BASIC);
            userRepository.save(user);
            return "done";
        } else return "username is busy ";
    }

    // szukanie usera by id
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Could not find user: " + id));
    }

    // usuniecie usera by id
    public String deleteUserById(Long id) {
        userRepository.deleteById(id);
        return "done";
    }

    //zmiana hasłaz
    public String changePassword(Long id, LoginCredentials credentials) {
        User user = findUserById(id);
        if (bcrypt.matches(credentials.getOldPassword(), user.getPassword())) {
            user.setPassword(bcrypt.encode(credentials.getNewPassword()));
            userRepository.save(user);
            return "changed password";
        }
        return "incorrect old password";
    }

    // zmiana profilu
    public String changeProfile(Long id, User user) {
        if (id == user.getId() && findUserById(id).getId() == user.getId()) {
            userRepository.save(user);
            return "updated";
        } else return "id or user is incorrect";
    }

    // dodanie usera do room
    public String addUserToRoomById(Long userId, Long roomId) {
        Optional<Room> room = roomService.findRoomById(roomId);
        User user = findUserById(userId);

        if (room.get().getUsers().size() < room.get().getSlots()) {
            List<Room> rooms = user.getRooms();
            rooms.add(room.get());
            user.setRooms(rooms);
            userRepository.save(user);
            return "added user";
        } else return "max users";
    }

    // dodanie usera do team.
    public String addUserToTeamById(Long userId, Long teamId) {
        Optional<Team> team = teamService.findTeamById(teamId);
        User user = findUserById(userId);
        if (team.get().getSlots() > team.get().getUsers().size()) {
            List<Team> teams = user.getTeams();
            teams.add(team.get());
            user.setTeams(teams);
            userRepository.save(user);
            return "added user";
        } else return "max users";

    }

    //usunięcie usera z team
    public String deleteUserFromTeamById(Long userId, Long teamId) {
        Optional<Team> team = teamService.findTeamById(teamId);
        User user = findUserById(userId);
        List<Team> teams = user.getTeams();
        teams.remove(team.get());
        user.setTeams(teams);
        userRepository.save(user);
        return "deleted user";
    }

    //usunięcie usera z room
    public String deleteUserFromRoomById(Long userId, Long roomId) {
        Optional<Room> room = roomService.findRoomById(roomId);
        User user = findUserById(userId);
        List<Room> rooms = user.getRooms();
        rooms.remove(room.get());
        user.setRooms(rooms);
        userRepository.save(user);
        return "deleted user";
    }
    //TODO  dodanie usera z google auth

    //TODO  Odczytanie powiadomienia

    private String readNotofication(Long notificationId, Long userId) {
        return "read";
    }
}