package pl.lukaszg.sportapp.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("Select t From Team t ")
    List<Team> findAllTeams(Pageable page);

    @Query("Select t From Team t WHERE t.id = : id")
    Team findByIdDto(@Param("id") Long id);
}
