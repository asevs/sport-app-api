package pl.lukaszg.sportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.User;
import pl.lukaszg.sportapp.model.UserLoginType;
import pl.lukaszg.sportapp.model.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    BCryptPasswordEncoder bcrypt;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String registerUser(User user) {
        if(userRepository.findByUsername(user.getUsername())){
            String password = user.getPassword();
            user.setPassword(bcrypt.encode(password));
            user.setCreatedUserDate(LocalDateTime.now());
            user.setLoginType(UserLoginType.BASIC);
            userRepository.save(user);
            return "done";
        }
        else return "username is busy ";
    }
}
