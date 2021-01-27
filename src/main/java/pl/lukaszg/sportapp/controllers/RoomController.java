package pl.lukaszg.sportapp.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pl.lukaszg.sportapp.dto.RoomDto;
import pl.lukaszg.sportapp.dto.RoomDtoMapper;
import pl.lukaszg.sportapp.model.Discipline;
import pl.lukaszg.sportapp.model.Room;
import pl.lukaszg.sportapp.services.RoomService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }


    // 1. Dodanie roomu
    @PostMapping("/")
    public String addRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }


    // 2. edycja roomu
    @PutMapping("/")
    public String editRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }

    // 3. wysłanie zaproszenia
    @GetMapping("/sendInvite/{roomId}/user/{userId}")
    public String sendInvite(@PathVariable Long roomId, @PathVariable Long userId) {
        return roomService.sendInvite(userId, roomId);
    }

    // 4. anulowanie zaproszenia
    @GetMapping("/cancelInvite/{roomId}/user/{userId}")
    public String cancelInvite(@PathVariable Long roomId, @PathVariable Long userId) {
        return roomService.cancelInvite(userId, roomId);
    }
    // 4. usunięcie kogoś z roomu

    @GetMapping("/removeUser/{roomId}/user/{userId}")
    public String removeUser(@PathVariable Long roomId, @PathVariable Long userId) {
        return roomService.removeUser(userId, roomId);
    }
    // 5. zamknięcie roomu ( zakonczenie meczu, wpisanie wynikow i statystyk)

    @GetMapping("/close/user/{ownerId}")
    public String closeRoom(@PathVariable Long ownerId, @RequestBody Room room) {
        return roomService.closeRoom(ownerId, room);
    }

    // 6. wymieszanie zawodników ( stworzenie teamow)
    @GetMapping("/{id}/randomize")
    public String randomizeTeams(@PathVariable Long id) {
        return roomService.randomizeTeams(id);
    }

    // 8. filtrowanie roomu

    // TODO - po dacie

    // TODO - po lokalizacji


    // - po opłacie
    @GetMapping("/filter")
    public Page<Room> getFilterRooms(@RequestParam(required = false) int pageNumber,
                                     Sort.Direction sort,
                                     Discipline discipline,
                                     Boolean isPriced,
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
                                     double lat, double lon, int radius) {
        return roomService.getByFilter(pageNumber, sort, discipline, isPriced, dateFrom, dateTo, lat, lon, radius);
    }

    @GetMapping("/dto/")
    public List<RoomDto> getRoomsDto(@RequestParam(required = false) int pageNumber, Sort.Direction sort) {
        return RoomDtoMapper.mapToRoomDtos(roomService.getRooms(pageNumber, sort));
    }


    //10.

    @PutMapping(value = "/{userId}/rooms/{roomId}", produces = "application/json")
    public String addUserToRoomById(@PathVariable("userId") Long userId, @PathVariable("roomId") Long roomId) {
        return roomService.addUserToRoomById(userId, roomId);
    }

    @DeleteMapping(value = "/{userId}/rooms/{roomId}", produces = "application/json")
    public String deleteUserFromRoomById(@PathVariable("userId") Long userId, @PathVariable("roomId") Long roomId) {
        return roomService.deleteUserFromRoomById(userId, roomId);
    }
}
