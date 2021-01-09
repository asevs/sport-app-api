package pl.lukaszg.sportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lukaszg.sportapp.model.Team;
import pl.lukaszg.sportapp.service.TeamService;

@RestController
@RequestMapping("api/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    //1 szukanie teamu po id
    public Team findTeamById(Long id) {
        return teamService.findTeamById(id);
    }

    //lista teamow z danymi
    // zawodnicy
    //ostatnie mecze z page 3
    // ostatnie turnieje z page 3
    // trofea
}
