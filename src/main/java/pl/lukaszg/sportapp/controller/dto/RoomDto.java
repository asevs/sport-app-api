package pl.lukaszg.sportapp.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import pl.lukaszg.sportapp.model.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class RoomDto {
    private long id;
    private double price;
    private Skill levelSkill;
    private LocalDateTime eventDate;
    private int slots;
    private Place place;
    private List<User> users;

}
