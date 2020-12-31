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
    NotificationRepository notificationRepository;
    @Autowired
    TeamService teamService;
    @Autowired
    RoomService roomService;
    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public List<User> findAll() {
        return userRepository.findAll();
    }

    //TODO  dodanie usera z google auth


    //Sprawdzamy czy user znajduje się w bazie. Jeżeli nie to tworzymy usera i zwracamy done. Jeżeli istnieje to zwracamy busy
    public String registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername())) {
            return "username is busy";
        } else {
            String password = user.getPassword();
            user.setPassword(bcrypt.encode(password));
            user.setCreatedUserDate(LocalDateTime.now());
            user.setLoginType(UserLoginType.BASIC);
            userRepository.save(user);
            return "done";
        }
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

    //zmiana hasła
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
        Room room = roomService.findRoomById(roomId);
        User user = findUserById(userId);

        if (room.getUsers().size() < room.getSlots()) {
            List<Room> rooms = user.getRooms();
            rooms.add(room);
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
        Room room = roomService.findRoomById(roomId);
        User user = findUserById(userId);
        List<Room> rooms = user.getRooms();
        rooms.remove(room);
        user.setRooms(rooms);
        userRepository.save(user);
        return "deleted user";
    }

    // Odczytanie powiadomienia

    public String readNotification(Long notificationId) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if (notification.isPresent()) {
            notification.get().setRead(true);
            notification.get().setReadDate(LocalDateTime.now());
            notificationRepository.save(notification.get());
            return "read";
        } else {
            return "incorrect id";
        }
    }

}