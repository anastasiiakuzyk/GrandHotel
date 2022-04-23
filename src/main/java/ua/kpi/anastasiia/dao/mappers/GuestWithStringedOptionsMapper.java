package ua.kpi.anastasiia.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.kpi.anastasiia.models.guest.Guest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestWithStringedOptionsMapper implements RowMapper<Guest> {
    @Override
    public Guest mapRow(ResultSet rs, int rowNum) throws SQLException {
        Guest guest = new Guest();
        guest.setId(rs.getInt(1));
        guest.setFirstName(rs.getString(2));
        guest.setLastName(rs.getString(3));
        guest.setPhone(rs.getString(4));
        guest.setPassport(rs.getString(5));
        guest.setEmail(rs.getString(6));
        guest.setOptionsList(rs.getString(7));
        return guest;
    }
}
