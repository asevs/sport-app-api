package pl.lukaszg.sportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.Room;
import pl.lukaszg.sportapp.model.RoomRepository;

import java.util.List;


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


}
