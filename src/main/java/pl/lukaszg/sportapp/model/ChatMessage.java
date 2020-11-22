package pl.lukaszg.sportapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private long id;
    @ManyToOne
    private Chat chat;
    @OneToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
    private User owner;
    @Column(name = "created_date")
    private Date createdDate;


}
