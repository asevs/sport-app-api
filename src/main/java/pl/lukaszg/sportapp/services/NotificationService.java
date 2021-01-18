package pl.lukaszg.sportapp.services;

import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.Notification;
import pl.lukaszg.sportapp.model.NotificationType;
import pl.lukaszg.sportapp.model.User;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;

@Service("notificationService")
public class NotificationService {
    private final UserService userService;
    private final MailService mailService;

    public NotificationService(UserService userService, MailService mailService) {

        this.userService = userService;
        this.mailService = mailService;
    }

    public void createNotification(Long userId, NotificationType type) {
        User user = userService.findUserById(userId);
        List<Notification> notifications = user.getNotifications();
        Notification notification = new Notification();
        notification.setType(type);
        notification.setCreatedDate(LocalDateTime.now());
        user.setNotifications(notifications);
        userService.changeProfile(user.getId(), user);
        if (user.isSendMail()) {
            try {
                mailService.sendMail(user.getEmail(), type.toString(), "New" + type.toString(), false);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

}
