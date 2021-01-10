package pl.lukaszg.sportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.controller.dto.TeamDto;
import pl.lukaszg.sportapp.model.Room;
import pl.lukaszg.sportapp.model.Team;
import pl.lukaszg.sportapp.model.TeamRepository;
import pl.lukaszg.sportapp.service.exceptions.ItemNotFoundException;

import java.util.List;


@Service("teamService")
public class TeamService {
    private static final int PAGE_SIZE = 20;
    
    @Autowired
    TeamRepository teamRepository;

    // 1. szukanie teamu po id
    public Team findTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Could not find team: " + id));
    }

    public List<Team> getTeams(int pageNumber, Sort.Direction sort) {
        return teamRepository.findAllTeams(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(sort, "id")));

    }
}
