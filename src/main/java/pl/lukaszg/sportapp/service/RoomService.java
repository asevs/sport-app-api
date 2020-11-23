package pl.lukaszg.sportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.Room;
import pl.lukaszg.sportapp.model.RoomRepository;

import java.util.List;
import java.util.Optional;


@Service("roomService")
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    //return room by id or empty
    public Optional<Room> findRoomById(Long id) {
        if (id != null && id > 0) return roomRepository.findById(id);
        else return Optional.empty();
    }


    public void updateRoom(Optional<Room> room) {
        room.ifPresent(value -> roomRepository.save(value));
    }
}
