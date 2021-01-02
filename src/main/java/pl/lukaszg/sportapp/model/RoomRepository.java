package pl.lukaszg.sportapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("Select r From Room r ")
    List<Room> findAllRooms(Pageable page);

    List<Room> findByDiscipline(Discipline discipline);

    @Query("Select r From Room r where r.isPriced = :isPriced ")
    List<Room> findByIsPriced(@Param("isPriced") boolean isPriced);
}
