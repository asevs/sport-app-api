package pl.lukaszg.sportapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "places")
@NoArgsConstructor
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
    @Column(name = "place_lon")
    private double lon;
    @Column(name = "place_lat")
    private Double lat;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "place_manager")
    private User manager;
    @OneToMany(mappedBy = "place")
    @JsonBackReference(value = "place-room")
    private List<Room> rooms;

}