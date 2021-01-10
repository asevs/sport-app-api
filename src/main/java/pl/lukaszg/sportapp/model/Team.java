package pl.lukaszg.sportapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "teams")
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;
    @Column(name = "team_name")
    @NotNull
    private String name;
    @OneToOne(mappedBy = "myTeam")
    @JsonManagedReference(value = "user-team")
    private User owner;
    @Column(name = "team_goals")
    private int goals;
    @Column(name = "team_assists")
    private int assists;
    @Column(name = "team_slots")
    private int slots;
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "teams", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "users-team")
    private List<User> users;
    @OneToMany(mappedBy = "winner")
    @JsonBackReference(value = "team-winner")
    private List<Room> winners;
    @OneToMany(mappedBy = "lost")
    @JsonBackReference(value = "team-lost")
    private List<Room> lost;
    @ManyToMany (fetch = FetchType.LAZY)
    @JsonBackReference(value = "team-draw")
    private List <Room> draws;

}