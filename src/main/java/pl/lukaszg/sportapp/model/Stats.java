package pl.lukaszg.sportapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@Table(name = "stats")
@NoArgsConstructor
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stats_id")
    Long id;
    @ElementCollection
    @OneToMany(mappedBy = "statsOwner")
    @MapKeyJoinColumn(name = "user_id")
    private Map<Integer, User> minutesPlayed;
    @ElementCollection
    @OneToMany(mappedBy = "statsOwner")
    @MapKeyJoinColumn(name = "user_id")
    private Map<Integer, User> goals;
    @ElementCollection
    @OneToMany(mappedBy = "statsOwner")
    @MapKeyJoinColumn(name = "user_id")
    private Map<Integer, User> assists;
    @ElementCollection
    @OneToMany(mappedBy = "statsOwner")
    @MapKeyJoinColumn(name = "user_id")
    private Map<Integer, User> yellowCards;
    @ElementCollection
    @OneToMany(mappedBy = "stats_")
    @MapKeyJoinColumn(name = "user_id")
    private Map<Integer, User> redCards;


}
