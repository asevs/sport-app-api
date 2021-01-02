package pl.lukaszg.sportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.lukaszg.sportapp.controller.dto.RoomDto;
import pl.lukaszg.sportapp.controller.dto.RoomDtoMapper;
import pl.lukaszg.sportapp.model.Discipline;
import pl.lukaszg.sportapp.model.Room;
import pl.lukaszg.sportapp.service.RoomService;

import java.util.List;

@RestController()
@RequestMapping("api/rooms")
public class RoomController {
    @Autowired
    RoomService roomService;

    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }


    // 1. Dodanie roomu
    @PostMapping("/")
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
    // - po dyscyplinie
    @GetMapping("/discipline/{discipline}")
    public List<Room> getByDiscipline(@PathVariable("discipline")Discipline discipline) {
        return roomService.findByDiscipline(discipline);
    }
    // TODO - po dacie

    // TODO - po lokalizacji

    // - po opłacie
    @GetMapping("/price/{isPriced}")
    public List<Room> getByPrice(@PathVariable("isPriced") boolean isPriced) {
        return roomService.getByPrice(isPriced);
    }


    // 9. stronicowanie roomow

    @GetMapping("/list/")
    public List<RoomDto> getRoomsDto(@RequestParam(required = false) int pageNumber, Sort.Direction sort) {

        return RoomDtoMapper.mapToRoomDtos(roomService.getRooms(pageNumber, sort));
    }


    //10.

}
