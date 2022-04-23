package ua.kpi.anastasiia.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.kpi.anastasiia.dao.mappers.FullBookingMapper;
import ua.kpi.anastasiia.dao.mappers.LastIdMapper;
import ua.kpi.anastasiia.models.booking.Booking;
import ua.kpi.anastasiia.models.booking.FullBooking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FullBookingDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FullBookingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FullBooking> getBookings() {
        return jdbcTemplate.query("SELECT * FROM full_booking", new FullBookingMapper());
    }

    public Object getBooking(int id) {
        return null;
    }

    public void addBooking(Booking booking) {
        jdbcTemplate.update("INSERT INTO booking(guest_id, room_id, arriving, leaving)" +
                        "VALUES(?, ?, ?, ?)",
                booking.getGuestId(), booking.getRoomId(), booking.getArriving(), booking.getLeaving());
    }

    public void removeBooking(int id) {
        jdbcTemplate.update("DELETE FROM booking WHERE id=?", id);
    }

    public boolean isGuestAlreadyBooked(int guestId, Date arriving, Date leaving) {
        List<Integer> integers = jdbcTemplate.query("SELECT COUNT(1) " +
                        "FROM booking " +
                        "WHERE (booking.arriving BETWEEN ? and ? or booking.leaving BETWEEN ? and ? or ? between booking.arriving and booking.leaving or ? between booking.arriving and booking.leaving) and booking.guest_id = ?"
                , new LastIdMapper(), arriving, leaving, arriving, leaving, arriving, leaving, guestId);
        if (integers.isEmpty()) {
            return false;
        }
        return integers.get(0) != 0;
    }

    public List<Integer> filterRoomsByAvailability(List<Integer> roomsId, Date arriving, Date leaving) {
        List<Integer> filteredRoomsId = new ArrayList<>();
        for (Integer roomId : roomsId) {
            List<Integer> integers = jdbcTemplate.query("SELECT COUNT(1) " +
                            "FROM booking " +
                            "WHERE (booking.arriving BETWEEN ? and ? or booking.leaving BETWEEN ? and ? or ? between booking.arriving and booking.leaving or ? between booking.arriving and booking.leaving) and booking.room_id = ?"
                    , new LastIdMapper(), arriving, leaving, arriving, leaving, arriving, leaving, roomId);
            if (integers.isEmpty()) {
                continue;
            }
            if (integers.get(0) == 0) {
                filteredRoomsId.add(roomId);
            }
        }
        return filteredRoomsId;
    }
}
