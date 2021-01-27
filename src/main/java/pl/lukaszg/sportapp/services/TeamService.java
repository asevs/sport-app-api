package pl.lukaszg.sportapp.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.repositories.CompetitionRepository;
import pl.lukaszg.sportapp.repositories.RoomRepository;
import pl.lukaszg.sportapp.model.Team;
import pl.lukaszg.sportapp.repositories.TeamRepository;
import pl.lukaszg.sportapp.services.exceptions.ItemNotFoundException;

import java.util.List;


@Service("teamService")
public class TeamService {
    private static final int PAGE_SIZE = 20;
    private final TeamRepository teamRepository;
    private final RoomRepository roomRepository;
    private final CompetitionRepository competitionRepository;

    public TeamService(TeamRepository teamRepository, RoomRepository roomRepository, CompetitionRepository competitionRepository) {
        this.teamRepository = teamRepository;
        this.roomRepository = roomRepository;
        this.competitionRepository = competitionRepository;
    }

    // 1. szukanie teamu po id
    public Team findTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Could not find team: " + id));
    }

    public Team findTeamByIdDto(Long id) {
        Team team = teamRepository.findByIdDto(id);
        team.setWinners(roomRepository.findAllRooms(PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "eventDate"))));
        team.setCompetitions(competitionRepository.findAllCompetitions(PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "eventDate"))));
        return team;
    }

    public List<Team> getTeams(int pageNumber, Sort.Direction sort) {
        return teamRepository.findAllTeams(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(sort, "id")));

    }
}
