package pl.lukaszg.sportapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "rooms")
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
    @Column(name = "room_discipline")
    private Discipline  discipline;
    @Column(name = "room_is_priced")
    private boolean isPriced;
    @Column(name = "room_score_team_first")
    private int scoreTeamFirst;
    @Column(name = "room_score_team_second")
    private int scoreTeamSecond;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Skill.class)
    @JoinColumn(name = "room_level_skill")
    private Skill levelSkill;
    @Column(name = "room_created_date")
    private LocalDateTime createdDate;
    @Column(name = "room_event_date")
    private LocalDateTime eventDate;
    @Column(name = "room_closed_date")
    private LocalDateTime closedDate;
    @Column(name = "slots")
    private int slots;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference(value = "owner-room")
    private User ownerUser;
    @OneToOne
    private User capitanSecondTeam;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference(value = "place-room")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Place place;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Team.class)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Team teamSecond;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Team.class)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Team teamFirst;
    @ManyToMany(mappedBy = "rooms", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "users-rooms")
    private List<User> users;
    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private Chat chat;
    @OneToOne
    private Stats stats;
    @ManyToMany(mappedBy = "roomInvites", fetch = FetchType.LAZY)
    private List<User> invitedUsers;
    @ManyToOne (fetch = FetchType.LAZY)
    @JsonManagedReference(value="team-winner")
    private Team winner;
    @ManyToOne (fetch = FetchType.LAZY)
    @JsonManagedReference(value="team-lost")
    private Team lost;
    @ManyToMany (fetch = FetchType.LAZY)
    @JsonManagedReference(value="team-draw")
    private List<Team> draws;



}