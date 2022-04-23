package ua.kpi.anastasiia.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import ua.kpi.anastasiia.dao.mappers.RoomMapper;
import ua.kpi.anastasiia.dao.mappers.RoomWithStringedOptionsMapper;
import ua.kpi.anastasiia.models.room.Room;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
public class RoomDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Room> getRooms() {
        return jdbcTemplate.query("SELECT rooms.id,rooms.type_id, rt.type ,rt.description, rooms.price " +
                "from rooms, room_types as rt where rt.id = type_id", new RoomMapper());
    }

    public Room getRoom(int id) {
        return jdbcTemplate.query("SELECT rooms.id, rooms.type_id, rt.type ,rt.description, rooms.price " +
                        "from rooms, room_types as rt where rt.id = type_id and rooms.id =?", new RoomMapper(), id)
                .stream().findAny().orElse(null);
    }

    public List<Room> getRoomWithOptions() {
        return jdbcTemplate.query("\n" +
                "SELECT rooms.id,rooms.type_id, rt.type ,rt.description, rooms.price, GROUP_CONCAT(r.name SEPARATOR ', ') as options from rooms\n" +
                "left join room_types rt on rt.id = type_id\n" +
                "left join room_option ro on rooms.id = ro.room_id\n" +
                "left join room_options r on ro.option_id = r.id\n" +
                "GROUP BY rooms.id, rooms.type_id, rt.type, rt.description, rooms.price", new RoomWithStringedOptionsMapper());
    }

    public void addRoom(Room room) {

        try {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
               PreparedStatement statement = con.prepareStatement("INSERT INTO rooms(type_id, price) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
               statement.setInt(1, room.getTypeId());
               statement.setDouble(2, room.getPrice());
               return statement;
            }, holder);
            for (int i = 0; i < room.getOptions().size(); i++) {
                jdbcTemplate.update("INSERT INTO room_option(room_id, option_id) VALUES (?, ?)", holder.getKey(), room.getOptions().get(i));
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateRoom(int id, Room room) {
        jdbcTemplate.update("UPDATE rooms SET type_id=?, price=? where id=?",
                room.getTypeId(), room.getPrice(), id);
        jdbcTemplate.update("DELETE FROM room_option WHERE room_id = ?", id);
        for (int i = 0; i < room.getOptions().size(); i++) {
            jdbcTemplate.update("INSERT INTO room_option(room_id, option_id) VALUES (?, ?)", id, room.getOptions().get(i));
        }
    }

    public void removeRoom(int id) {
        jdbcTemplate.update("DELETE FROM room_option WHERE room_id = ?", id);
        jdbcTemplate.update("DELETE FROM rooms WHERE id=?", id);
    }
}
