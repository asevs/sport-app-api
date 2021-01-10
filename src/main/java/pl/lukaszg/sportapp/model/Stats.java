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
    @OneToMany
    @MapKeyJoinColumn(name = "user_id")
    private Map<Integer, User> minutesPlayed;
    @OneToMany
    @MapKeyJoinColumn(name = "user_id")
    private Map<Integer, User> goals;
    @OneToMany
    @MapKeyJoinColumn(name = "user_id")
    private Map<Integer, User> assists;
    @OneToMany
    @MapKeyJoinColumn(name = "user_id")
    private Map<Integer, User> yellowCards;
    @OneToMany
    @MapKeyJoinColumn(name = "user_id")
    private Map<Integer, User> redCards;
    @Column(name = "match_win")
    private int matchWin;
    @Column(name = "match_lost")
    private int matchLost;
    @Column(name = "match_draw")
    private int matchDraw;
    @Column(name = "cup_win")
    private int cupWin;
    @Column(name = "league_win")
    private int leagueWin;
}
