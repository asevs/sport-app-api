import lombok.Data;
import pl.lukaszg.sportapp.model.User;

import javax.persistence.*;

@Entity
@Data
@Table(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private int id;
    @Column(name = "place_name")
    private String name;
    @Column(name = "place_city")
    private String city;
    @Column(name = "place_street")
    private String street;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "place_manager")
    private User manager;

}