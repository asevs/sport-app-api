package pl.lukaszg.sportapp.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private long id;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "is_read")
    private boolean isRead;
    @Column(name = "read_date")
    private LocalDateTime readDate;
    @Column(name = "type")
    private NotificationType type;
}
