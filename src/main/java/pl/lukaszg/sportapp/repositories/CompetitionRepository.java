package pl.lukaszg.sportapp.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.lukaszg.sportapp.model.Competition;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    @Query("Select c From Competition c")
    List<Competition> findAllCompetitions(Pageable page);
}
