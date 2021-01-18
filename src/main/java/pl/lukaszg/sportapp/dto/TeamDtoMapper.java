package pl.lukaszg.sportapp.dto;

import pl.lukaszg.sportapp.model.Team;

import java.util.List;
import java.util.stream.Collectors;

public class TeamDtoMapper {
    private TeamDtoMapper() {
    }

    public static List<TeamDto> mapToTeamDtos(List<Team> teams) {
        return teams.stream()
                .map(team -> mapToTeamDtos(team))
                .collect(Collectors.toList());
    }

    public static TeamDto mapToTeamDtos(Team team) {
        return TeamDto.builder()
                .id(team.getId())
                .discipline(team.getDiscipline())
                .owner(team.getOwner())
                .build();
    }
}
