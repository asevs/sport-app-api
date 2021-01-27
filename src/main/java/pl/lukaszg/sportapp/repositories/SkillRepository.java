package pl.lukaszg.sportapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukaszg.sportapp.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
}
