package pl.lukaszg.sportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lukaszg.sportapp.model.Room;
import pl.lukaszg.sportapp.service.RoomService;

import java.util.List;

@RestController("api/rooms")
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping("/room/allRooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }


    // 1. Dodanie roomu
    @PostMapping(value = "/", produces = "application/json")
    public String addRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }


    // 2. edycja roomu

    // 3. wysłanie zaproszenia

    // 4. anulowanie zaproszenia

    // 4. usunięcie kogoś z roomu

    // 5. zamknięcie roomu ( zakonczenie meczu, wpisanie wynikow i statystyk)

    // 6. wymieszanie zawodników ( stworzenie teamow)

    // 7. sortowanie roomow

    // 8. filtrowanie roomu

    // 9. stronicowanie roomow

    //10.

}
