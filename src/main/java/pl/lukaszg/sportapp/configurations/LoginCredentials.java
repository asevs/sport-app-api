package pl.lukaszg.sportapp.configurations;

import lombok.Getter;

@Getter
public class LoginCredentials {
    private String username;
    private String password;
    private String oldPassword;
    private String newPassword;
}
