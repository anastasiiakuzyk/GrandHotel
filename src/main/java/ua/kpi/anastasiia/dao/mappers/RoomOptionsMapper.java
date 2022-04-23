package ua.kpi.anastasiia.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.kpi.anastasiia.models.room.RoomOptions;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomOptionsMapper implements RowMapper<RoomOptions> {
    @Override
    public RoomOptions mapRow(ResultSet rs, int rowNum) throws SQLException {
        RoomOptions options = new RoomOptions();
        options.setId(rs.getInt(1));
        options.setName(rs.getString(2));
        return options;
    }
}
