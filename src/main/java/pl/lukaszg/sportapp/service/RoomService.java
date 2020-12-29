package pl.lukaszg.sportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.Place;
import pl.lukaszg.sportapp.model.Room;
import pl.lukaszg.sportapp.model.RoomRepository;
import pl.lukaszg.sportapp.model.User;
import pl.lukaszg.sportapp.service.exceptions.ItemNotFoundException;

import java.util.Date;
import java.util.List;


@Service("roomService")
public class RoomService {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserService userService;
    @Autowired
    PlaceService placeService;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // 1. Dodanie roomu: //
    // ? o tej samej porze ma mecz właściciel,
    // ? o tej samej porze ta sama dyscyplina na tym samym boisku

    public String addRoom(Room room) {
        User owner = userService.findUserById(room.getOwnerUser().getId());
        Place place = placeService.findPlaceById(room.getOwnerUser().getId());
        int imposedDatesPlaces = checkRoomsByEventDate(room.getEventDate(), place.getRooms());
        int imposedDatesRooms = checkRoomsByEventDate(room.getEventDate(), owner.getRooms());
        if (imposedDatesPlaces != 0) return "imposed " + imposedDatesPlaces + "room in this place";
        else if (imposedDatesRooms != 0) return "imposed " + imposedDatesRooms + "room in your rooms";
        else {
            roomRepository.save(room);
            return "save room";
        }
    }

    // 2. edycja roomu
    public String changeRoom(Long ownerId, Room room) {
        if (ownerId == room.getOwnerUser().getId() && findRoomById(room.getId()) != null) {
            roomRepository.save(room);
            return "updated";
        } else return "you haven't editing roles";
    }
    // 3. wysłanie zaproszenia

    // 4. anulowanie zaproszenia

    // 4. usunięcie kogoś z roomu

    // 5. zamknięcie roomu ( zakonczenie meczu, wpisanie wynikow i statystyk)

    // 6. wymieszanie zawodników ( stworzenie teamow)

    // 7. sortowanie roomow

    // 8. filtrowanie roomu

    // 9. stronicowanie roomow

    //10. sprawdzenie czy ktorys z roomow ma taka sama date eventu
    private int checkRoomsByEventDate(Date date, List<Room> rooms) {
        int roomsCount = 0;
        for (Room fRoom : rooms) {
            if (fRoom.getEventDate().compareTo(date) == 0) {
                roomsCount++;
            }
        }
        return roomsCount;
    }


    // 12. szukanie roomu po id
    public Room findRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Could not find room: " + id));
    }
}
