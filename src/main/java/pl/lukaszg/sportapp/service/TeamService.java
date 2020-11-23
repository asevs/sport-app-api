package pl.lukaszg.sportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.Team;
import pl.lukaszg.sportapp.model.TeamRepository;

import java.util.Optional;

@Service("teamService")
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    //return team by id or empty
    public Optional<Team> findTeamById(Long id) {
        if (id != null && id > 0) return teamRepository.findById(id);
        else return Optional.empty();
    }
}
