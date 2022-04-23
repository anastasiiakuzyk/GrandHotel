package ua.kpi.anastasiia.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.kpi.anastasiia.models.room.RoomStringedOptions;

import java.sql.ResultSet;
import java.sql.SQLException;


public class RoomStringedOptionsMapper implements RowMapper<RoomStringedOptions> {
    @Override
    public RoomStringedOptions mapRow(ResultSet rs, int rowNum) throws SQLException {
        RoomStringedOptions options = new RoomStringedOptions();
        options.setRoomId(rs.getInt(1));
        options.setStringedOptions(rs.getString(2));
        return options;
    }
}
