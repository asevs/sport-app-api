package pl.lukaszg.sportapp.dto;

import pl.lukaszg.sportapp.model.Room;

import java.util.List;
import java.util.stream.Collectors;

public class RoomDtoMapper {

    private RoomDtoMapper() {
    }

    //mapowanie roomu na strone roomow
    public static List<RoomDto> mapToRoomDtos(List<Room> rooms) {
        return rooms.stream()
                .map(room -> mapToRoomDtos(room))
                .collect(Collectors.toList());
    }

    public static RoomDto mapToRoomDtos(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .price(room.getPrice())
                .levelSkill(room.getLevelSkill())
                .eventDate(room.getEventDate())
                .slots(room.getSlots())
                .place(room.getPlace())
                .users(room.getUsers())
                .build();
    }
}
