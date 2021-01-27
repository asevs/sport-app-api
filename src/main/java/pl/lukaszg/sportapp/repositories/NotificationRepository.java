package pl.lukaszg.sportapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukaszg.sportapp.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
