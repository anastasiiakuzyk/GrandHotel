package ua.kpi.anastasiia.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.kpi.anastasiia.dao.mappers.RoomTypeMapper;
import ua.kpi.anastasiia.models.room.RoomType;

import java.util.List;

@Component
public class RoomTypeDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomTypeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<RoomType> getRoomTypes() {
        return jdbcTemplate.query("SELECT id, type, description " +
                "from room_types", new RoomTypeMapper());
    }
}