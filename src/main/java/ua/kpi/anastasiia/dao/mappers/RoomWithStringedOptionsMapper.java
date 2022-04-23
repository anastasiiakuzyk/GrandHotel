package ua.kpi.anastasiia.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.kpi.anastasiia.models.room.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomWithStringedOptionsMapper implements RowMapper<Room> {
    @Override
    public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt(1));
        room.setTypeId(rs.getInt(2));
        room.setType(rs.getString(3));
        room.setDescription(rs.getString(4));
        room.setPrice(rs.getInt(5));
        room.setOptionsList(rs.getString(6));
        return room;
    }
}
