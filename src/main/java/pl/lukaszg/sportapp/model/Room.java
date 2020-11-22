package pl.lukaszg.sportapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "room")
@NoArgsConstructor

public class Room {

    @Enumerated(EnumType.STRING)
    RoomStatus roomStatus;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private long id;
    @Column(name = "room_price")
    private double price;
    @Column(name = "room_score_team_first")
    private int scoreTeamFirst;
    @Column(name = "room_score_team_second")
    private int scoreTeamSecond;
    @OneToOne(cascade = CascadeType.MERGE, targetEntity = Skill.class)
    @JoinColumn(name = "room_level_skill")
    private Skill levelSkill;
    @Column(name = "room_created_date")
    private Date createdDate;
    @Column(name = "room_event_date")
    private Date eventDate;
    @Column(name = "slots")
    private int slots;
    @ManyToOne
    @JsonManagedReference(value = "owner-room")
    @JsonIgnore
    private User ownerUser;
    @OneToOne(cascade = CascadeType.MERGE, targetEntity = Place.class)
    private Place place;
    @OneToOne(cascade = CascadeType.MERGE, targetEntity = Team.class)
    private Team teamSecond;
    @OneToOne(cascade = CascadeType.MERGE, targetEntity = Team.class)
    private Team teamFirst;
    @ToString.Exclude
    @ManyToMany(mappedBy = "rooms", cascade = CascadeType.MERGE)
    @JsonIgnore
    @JsonManagedReference(value = "users-rooms")
    private List<User> users;
    @OneToOne
    @JsonBackReference(value = "stats")
    private Stats stats;
    @OneToOne(cascade = CascadeType.MERGE, targetEntity = Chat.class)
    private Chat chat;


}