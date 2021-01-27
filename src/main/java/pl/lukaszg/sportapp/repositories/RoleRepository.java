package pl.lukaszg.sportapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukaszg.sportapp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
