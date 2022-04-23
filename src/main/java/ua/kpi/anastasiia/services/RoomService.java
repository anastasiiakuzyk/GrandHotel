package ua.kpi.anastasiia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.anastasiia.dao.FullBookingDAO;
import ua.kpi.anastasiia.dao.GuestDAO;
import ua.kpi.anastasiia.dao.RoomDAO;
import ua.kpi.anastasiia.dao.RoomOptionDAO;
import ua.kpi.anastasiia.models.room.Room;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    FullBookingDAO bookings;

    @Autowired
    RoomOptionDAO roomOptions;

    @Autowired
    GuestDAO guests;

    @Autowired
    RoomDAO rooms;

    public List<Room> getRooms(Date arrivingDate, Date leavingDate, List<Integer> optionsId) {
        List<Room> roomsByGuest;
        if (!optionsId.isEmpty()) {
            List<Integer> roomsId = bookings.filterRoomsByAvailability(roomOptions.getRoomsIdByPersonOptions(optionsId), arrivingDate, leavingDate);
            roomsByGuest = new ArrayList<>();
            for (Integer roomId : roomsId) {
                roomsByGuest.add(rooms.getRoom(roomId));
            }
        } else {
            roomsByGuest = rooms.getRooms();
            List<Integer> roomsId = bookings.filterRoomsByAvailability(roomsByGuest.stream().map(Room::getId).collect(Collectors.toList()), arrivingDate, leavingDate);
            roomsByGuest = new ArrayList<>();
            for (Integer roomId : roomsId) {
                roomsByGuest.add(rooms.getRoom(roomId));
            }
        }
        return roomsByGuest;
    }
}
