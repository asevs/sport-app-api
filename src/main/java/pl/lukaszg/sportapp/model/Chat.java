package pl.lukaszg.sportapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private long id;
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private List<ChatMessage> messages;
    @OneToOne
    private Room room;
}
