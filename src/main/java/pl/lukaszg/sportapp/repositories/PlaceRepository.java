package pl.lukaszg.sportapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lukaszg.sportapp.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
