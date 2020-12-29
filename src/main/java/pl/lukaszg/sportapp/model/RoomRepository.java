package pl.lukaszg.sportapp.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean getRoomByEventDate(Date eventDate);
}
