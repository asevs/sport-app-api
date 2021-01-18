package pl.lukaszg.sportapp.services;

import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.Notification;
import pl.lukaszg.sportapp.model.NotificationType;
import pl.lukaszg.sportapp.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service("notificationService")
public class NotificationService {
    private final UserService userService;

    public NotificationService(UserService userService) {
        this.userService = userService;
    }

    public void createNotification(Long userId, NotificationType type) {
        User user = userService.findUserById(userId);
        List<Notification> notifications = user.getNotifications();
        Notification notification = new Notification();
        notification.setType(type);
        notification.setCreatedDate(LocalDateTime.now());
        user.setNotifications(notifications);
        userService.changeProfile(user.getId(),user);
    }

}
