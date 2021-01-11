package pl.lukaszg.sportapp.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "competitions")
@NoArgsConstructor
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competition_id")
    private Long id;
    @Column(name = "competition_name")
    private String name;
    @ManyToMany
    private List<Team> teams;
    @Column(name = "competition_event_date")
    LocalDateTime eventDate;


}
