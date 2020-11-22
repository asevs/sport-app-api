package pl.lukaszg.sportapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @MapKeyJoinColumn(name = "user_id")
    private Map<User, Integer> minutesPlayed;
    @ElementCollection
    @MapKeyJoinColumn(name = "user_id")
    private Map<User, Integer> goals;
    @ElementCollection
    @MapKeyJoinColumn(name = "user_id")
    private Map<User, Integer> assists;
    @ElementCollection
    @MapKeyJoinColumn(name = "user_id")
    private Map<User, Integer> yellowCards;
    @ElementCollection
    @MapKeyJoinColumn(name = "user_id")
    private Map<User, Integer> redCards;
    @OneToOne(mappedBy = "stats")
    @JsonManagedReference(value = "stats")
    private Room room;

}
