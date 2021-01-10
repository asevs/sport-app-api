package pl.lukaszg.sportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.lukaszg.sportapp.controller.dto.TeamDto;
import pl.lukaszg.sportapp.controller.dto.TeamDtoMapper;
import pl.lukaszg.sportapp.model.Team;
import pl.lukaszg.sportapp.service.TeamService;

import java.util.List;


@RestController
@RequestMapping("api/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    //1 szukanie teamu po id
    public Team findTeamById(Long id) {
        return teamService.findTeamById(id);
    }

    //team z danymi: nazwa,dyscyplina,statystyki,zawodnicy, ostatnie 3 mecez, ostatnie 3 udziały w turniehach/ligach
    public List<TeamDto> getTeamsDtos(@RequestParam(required = false) int pageNumber, Sort.Direction sort) {
        return TeamDtoMapper.mapToTeamDtos(teamService.getTeams(pageNumber, sort));

    }
    // zawodnicy
    //ostatnie mecze z page 3
    // ostatnie turnieje z page 3
    // trofea
}
