package ua.kpi.anastasiia.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.kpi.anastasiia.models.guest.PersonalOptions;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalOptionsMapper implements RowMapper<PersonalOptions> {
    @Override
    public PersonalOptions mapRow(ResultSet rs, int rowNum) throws SQLException {
        PersonalOptions options = new PersonalOptions();
        options.setId(rs.getInt(1));
        return options;
    }
}
