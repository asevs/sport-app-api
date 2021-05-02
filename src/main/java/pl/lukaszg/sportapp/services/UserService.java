package pl.lukaszg.sportapp.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.configurations.LoginCredentials;
import pl.lukaszg.sportapp.model.Notification;
import pl.lukaszg.sportapp.model.Team;
import pl.lukaszg.sportapp.model.User;
import pl.lukaszg.sportapp.model.UserLoginType;
import pl.lukaszg.sportapp.repositories.NotificationRepository;
import pl.lukaszg.sportapp.repositories.UserRepository;
import pl.lukaszg.sportapp.services.exceptions.ItemNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService {

    private final UserRepository userRepository;

    private final NotificationRepository notificationRepository;

    private final TeamService teamService;

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository,
                       NotificationRepository notificationRepository,
                       TeamService teamService
                       ) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.teamService = teamService;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    //TODO  dodanie usera z google auth


    //Sprawdzamy czy user znajduje się w bazie. Jeżeli nie to tworzymy usera i zwracamy done. Jeżeli istnieje to zwracamy busy
    public String registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) == user) {
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
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
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


    // dodanie usera do team.
    public String addUserToTeamById(Long userId, Long teamId) {
        Team team = teamService.findTeamById(teamId);
        User user = findUserById(userId);
        if (team.getSlots() > team.getUsers().size()) {
            List<Team> teams = user.getTeams();
            teams.add(team);
            user.setTeams(teams);
            userRepository.save(user);
            return "added user";
        } else return "max users";

    }

    //usunięcie usera z team
    public String deleteUserFromTeamById(Long userId, Long teamId) {
        Team team = teamService.findTeamById(teamId);
        User user = findUserById(userId);
        List<Team> teams = user.getTeams();
        teams.remove(team);
        user.setTeams(teams);
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