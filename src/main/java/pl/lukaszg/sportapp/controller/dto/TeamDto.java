package pl.lukaszg.sportapp.controller.dto;

import lombok.Builder;
import lombok.Getter;
import pl.lukaszg.sportapp.model.*;

import java.util.List;

@Getter
@Builder
public class TeamDto {
    private long id;
    private Discipline discipline;
    private String name;
    private User owner;
    private Stats stats;
    private List<Room> rooms;
    private List<Competition> competitions;
}
