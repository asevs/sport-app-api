package pl.lukaszg.sportapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.*;
import pl.lukaszg.sportapp.service.exceptions.ItemNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service("roomService")
@RequiredArgsConstructor
public class RoomService {

    private static final NotificationType ROOM_INVITE = NotificationType.ROOM_INVITE;
    private static final RoomStatus CLOSED = RoomStatus.CLOSED;
    private static final int PAGE_SIZE = 20;

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserService userService;
    @Autowired
    PlaceService placeService;
    @PersistenceContext
    EntityManager entityManager;

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
            room.setCreatedDate(LocalDateTime.now());
            roomRepository.save(room);
            return "save room";
        }
    }

    // 2. edycja roomu
    public String editRoom(Long ownerId, Room room) {
        if (ownerId == room.getOwnerUser().getId() && findRoomById(room.getId()) != null) {
            roomRepository.save(room);
            return "updated";
        } else return "you haven't editing roles";
    }

    // 3. wysłanie zaproszenia do roomu
    public String sendInvite(Long userId, Long roomId) {
        List<User> users = findRoomById(roomId).getInvitedUsers();
        Room room = findRoomById(roomId);
        users.add(userService.findUserById(userId));
        room.setInvitedUsers(users);
        roomRepository.save(room);
        createNotification(userId, ROOM_INVITE);
        return "sent invite";
    }

    // 4. anulowanie zaproszenia
    public String cancelInvite(Long userId, Long roomId) {
        List<User> users = findRoomById(roomId).getInvitedUsers();
        Room room = findRoomById(roomId);
        users.remove(userService.findUserById(userId));
        room.setInvitedUsers(users);
        roomRepository.save(room);
        return "canceled invite";

    }

    // 4. usunięcie kogoś z roomu
    public String removeUser(Long userId, Long roomId) {
        List<User> users = findRoomById(roomId).getUsers();
        Room room = findRoomById(roomId);
        users.remove(userService.findUserById(userId));
        room.setInvitedUsers(users);
        roomRepository.save(room);
        return "deleted user";

    }

    // 5. zamknięcie roomu ( zakonczenie meczu, wpisanie wynikow i statystyk)
    public String closeRoom(Long userId, Room room) {
        User owner = userService.findUserById(userId);
        Room editRoom = findRoomById(room.getId());
        editRoom.setRoomStatus(CLOSED);
        editRoom.setClosedDate(LocalDateTime.now());
        roomRepository.save(editRoom);
        return "closed room";
    }

    // 6. wymieszanie zawodników ( stworzenie teamow)
    public String randomizeTeams(Long roomId) {
        Room editRoom = findRoomById(roomId);
        List<User> teamFirst = editRoom.getUsers();
        List<User> teamSecond = new ArrayList<>();
        if (teamFirst.size() > 2) {
            Collections.shuffle(teamFirst, new Random(teamFirst.size()));
            for (int i = 1; i < teamFirst.size(); i++) {
                if (i % 2 == 0) teamSecond.add(teamFirst.get(i));
            }
            return "created teams";
        }
        return "too few players";
    }

    // 7. filtrowanie roomu
    public List<Room> findByDiscipline(Discipline discipline) {
        return roomRepository.findByDiscipline(discipline);
    }

    // 8. sortowanie roomow
    // 9. stronicowanie roomow
    public List<Room> getRooms(int pageNumber, Sort.Direction sort) {
        return roomRepository.findAllRooms(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(sort, "id")));

    }

    //10. sprawdzenie czy ktorys z roomow ma taka sama date eventu
    private int checkRoomsByEventDate(LocalDateTime date, List<Room> rooms) {
        int roomsCount = 0;
        for (Room fRoom : rooms) {
            if (fRoom.getEventDate().compareTo(date) == 0) {
                roomsCount++;
            }
        }
        return roomsCount;
    }

    // 11. szukanie roomu po id
    public Room findRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Could not find room: " + id));
    }

    // 12. stworzenie powiadomienia
    public void createNotification(Long userId, NotificationType notificationType) {
        Notification notification = new Notification();
        notification.setType(notificationType);
        notification.setCreatedDate(LocalDateTime.now());
    }

    public List<Room> getByPrice(boolean isPriced) {
        return roomRepository.findByIsPriced(isPriced);
    }

    public Page<Room> getByFilter(int pageNumber, Sort.Direction sort, Discipline discipline, Boolean isPriced) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> query = cb.createQuery(Room.class);
        Root<Room> r = query.from(Room.class);
        Predicate criteria = cb.conjunction();
        if (isPriced != null) {
            ParameterExpression<Boolean> paramIsPriced = cb.parameter(Boolean.class, "isPriced");
            criteria = cb.and(criteria, cb.equal(r.get("isPriced"), paramIsPriced));
        }
        if (discipline != null) {
            ParameterExpression<Discipline> paramDiscipline = cb.parameter(Discipline.class, "discipline");
            cb.and(criteria, cb.equal(r.get("discipline"), paramDiscipline));
        }
        query.select(r)
                .where(criteria);

        TypedQuery<Room> tq = entityManager.createQuery(query);
        Pageable page = PageRequest.of(pageNumber, 20, sort, "id");
        Page<Room> result = new PageImpl<Room>(tq.getResultList(), page, 20);
        return result;
    }

}
