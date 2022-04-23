package ua.kpi.anastasiia.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.kpi.anastasiia.models.booking.FullBooking;

import java.sql.ResultSet;
import java.sql.SQLException;

//
//    String firstName;
//            String lastName;
//            String guestPhone;
//            String passport;
//            String email;
//            String personalOptions;
//            int roomNumber;
//            String roomType;
//            int totalPrice;
//            Date guestArrive;
//            Date guestLeave;

public class FullBookingMapper implements RowMapper<FullBooking> {
    @Override
    public FullBooking mapRow(ResultSet rs, int rowNum) throws SQLException {
        FullBooking booking = new FullBooking();
        booking.setId(rs.getInt(1));
        booking.setGuestId(rs.getInt(2));
        booking.setFirstName(rs.getString(3));
        booking.setLastName(rs.getString(4));
        booking.setGuestPhone(rs.getString(5));
        booking.setPassport(rs.getString(6));
        booking.setEmail(rs.getString(7));
        booking.setPersonalOptions(rs.getString(8));

        booking.setRoomNumber(rs.getInt(9));

        booking.setRoomType(rs.getString(10));

        booking.setTotalPrice(rs.getInt(11));

        booking.setGuestArrive(rs.getDate(12));
        booking.setGuestLeave(rs.getDate(13));
        return booking;
    }
}
