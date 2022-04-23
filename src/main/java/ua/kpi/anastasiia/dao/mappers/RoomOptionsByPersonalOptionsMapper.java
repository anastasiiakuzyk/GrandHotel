package ua.kpi.anastasiia.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.kpi.anastasiia.models.room.RoomOptionsByPersonalOptions;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomOptionsByPersonalOptionsMapper implements RowMapper<RoomOptionsByPersonalOptions> {
    @Override
    public RoomOptionsByPersonalOptions mapRow(ResultSet rs, int rowNum) throws SQLException {
        RoomOptionsByPersonalOptions options = new RoomOptionsByPersonalOptions();
        options.setRoomOptionsId(rs.getInt(1));
        return options;
    }
}
