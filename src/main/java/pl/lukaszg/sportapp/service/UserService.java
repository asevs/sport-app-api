package pl.lukaszg.sportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.*;

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
    BCryptPasswordEncoder bcrypt;

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
    public Optional<User> findUserById(Long id) {
        if (id != null && id > 0) return userRepository.findById(id);
        else return Optional.empty();
    }

    //zmiana hasła
    public String changePassword(Long id, String oldPassword, String newPassword) {
        Optional<User> user = findUserById(id);
        if (user.isPresent()) {
            if (bcrypt.matches(oldPassword, user.get().getPassword())) {
                user.get().setPassword(bcrypt.encode(newPassword));
                userRepository.save(user.get());
                return "changed password";
            }
            return "incorrect old password";
        } else return "No user in db";
    }

    // zmiana profilu
    public String changeProfile(Long id, User user) {
        if (id == user.getId() && findUserById(id).isPresent()) {
            userRepository.save(user);
            return "updated";
        } else return "id or user is incorrect";
    }

    // dodanie usera do room
    public String addUserToRoomById(Long teamId, Long userId) {
        Optional<Room> room = roomService.findRoomById(teamId);
        Optional<User> user = findUserById(userId);

        if (room.isPresent() && user.isPresent()) {
            if (room.get().getUsers().size() < room.get().getSlots()) {
                room.get().getUsers().add(user.get());
                return "added user";
            } else return "max users";
        } else return "user or team is empty";
    }

    // dodanie usera do team.
    public String addUserToTeamById(Long teamId, Long userId) {
        Optional<Team> team = teamService.findTeamById(teamId);
        Optional<User> user = findUserById(userId);

        if (team.isPresent() && user.isPresent()) {
            if (team.get().getSlots() > team.get().getUsers().size()) {
                List<Team> teams = user.get().getTeams();
                teams.add(team.get());
                user.get().setTeams(teams);
                userRepository.save(user.get());
                return "added user";
            } else return "max users";
        } else return "user or team is empty";
    }

    //usunięcie usera z team
    public String deleteUserFromTeamById(Long teamId, Long userId) {
        Optional<Team> team = teamService.findTeamById(teamId);
        Optional<User> user = findUserById(userId);

        if (team.isPresent() && user.isPresent()) {
            user.get().getTeams().remove(team.get());
            userRepository.save(user.get());
            return "deleted user";
        } else return "user or team is empty";
    }

    //usunięcie usera z room
    public String deleteUserFromRoomById(Long roomId, Long userId) {
        Optional<Room> room = roomService.findRoomById(roomId);
        Optional<User> user = findUserById(userId);

        if (room.isPresent() && user.isPresent()) {
            room.get().getUsers().remove(user.get());
            roomService.updateRoom(room);
            return "deleted user";
        } else return "user or room is empty";
    }
    //TODO  dodanie usera z google auth

    //TODO  Odczytanie powiadomienia

    private String readNotofication(Long notificationId, Long userId) {
        return "read";
    }
}