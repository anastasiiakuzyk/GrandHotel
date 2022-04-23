package ua.kpi.anastasiia.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.kpi.anastasiia.models.guest.GuestOptions;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestOptionsMapper implements RowMapper<GuestOptions> {
    @Override
    public GuestOptions mapRow(ResultSet rs, int rowNum) throws SQLException {
        GuestOptions options = new GuestOptions();
        options.setId(rs.getInt(1));
        options.setName(rs.getString(2));
        return options;
    }
}
