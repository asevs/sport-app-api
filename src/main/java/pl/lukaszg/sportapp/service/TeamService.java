package pl.lukaszg.sportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.Team;
import pl.lukaszg.sportapp.model.TeamRepository;
import pl.lukaszg.sportapp.service.exceptions.ItemNotFoundException;


@Service("teamService")
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    // 1. szukanie teamu po id
    public Team findTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Could not find team: " + id));
    }
}
