package pl.lukaszg.sportapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("Select r From Room r")
    List<Room> findAllRooms(Pageable page);
}
