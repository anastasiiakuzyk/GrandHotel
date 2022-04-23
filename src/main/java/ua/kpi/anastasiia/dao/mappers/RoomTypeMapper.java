package ua.kpi.anastasiia.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.kpi.anastasiia.models.room.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomTypeMapper implements RowMapper<RoomType> {

    @Override
    public RoomType mapRow(ResultSet rs, int rowNum) throws SQLException {
        RoomType type = new RoomType();
        type.setId(rs.getInt(1));
        type.setType(rs.getString(2));
        type.setDescription(rs.getString(3));
        return type;
    }
}
