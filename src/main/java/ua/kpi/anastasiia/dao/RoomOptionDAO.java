package ua.kpi.anastasiia.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.kpi.anastasiia.dao.mappers.RoomOptionsByPersonalOptionsMapper;
import ua.kpi.anastasiia.dao.mappers.RoomOptionsMapper;
import ua.kpi.anastasiia.dao.mappers.RoomStringedOptionsMapper;
import ua.kpi.anastasiia.models.room.RoomOptions;
import ua.kpi.anastasiia.models.room.RoomOptionsByPersonalOptions;
import ua.kpi.anastasiia.models.room.RoomStringedOptions;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomOptionDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomOptionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<RoomStringedOptions> getRoomOptionsStringed() {
        return jdbcTemplate.query("SELECT room_id, GROUP_CONCAT(option_id SEPARATOR '') " +
                "from room_option " +
                "group by room_id", new RoomStringedOptionsMapper());
    }

    public List<RoomOptions> getRoomOptionsByRoomId(int roomId) {
        return jdbcTemplate.query("select po.id, po.name from room_option left join room_options po on po.id=option_id where room_id=?", new RoomOptionsMapper(), roomId);
    }

    public List<RoomOptions> getAllRoomOptions() {
        return jdbcTemplate.query("SELECT * FROM room_options", new RoomOptionsMapper());
    }

    private List<Integer> getRoomOptionsByPersonalOptions(List<Integer> personalOptionsId) {
        String questionMarks = "(";
        for (int i = 0; i < personalOptionsId.size(); i++) {
            if (i == personalOptionsId.size() - 1) {
                questionMarks += "?";
            } else questionMarks += "?,";
        }
        questionMarks = questionMarks + ")";
        String query = "SELECT room_options_id FROM room_options_personal_options WHERE personal_option_id IN " + questionMarks;
        List<RoomOptionsByPersonalOptions> options = jdbcTemplate.query(query, new RoomOptionsByPersonalOptionsMapper(), personalOptionsId.toArray());
        List<Integer> optionsId = new ArrayList<>();
        for (RoomOptionsByPersonalOptions option : options) {
            optionsId.add(option.getRoomOptionsId());
        }
        return optionsId;
    }

    public List<Integer> getRoomsIdByPersonOptions(List<Integer> personOptionsId) {
        List<RoomStringedOptions> roomOptions = getRoomOptionsStringed();
        List<Integer> personalOptions = getRoomOptionsByPersonalOptions(personOptionsId);
        List<Integer> roomsId = new ArrayList<>();

        for (RoomStringedOptions roomOption : roomOptions) {
            boolean containAll = true;
            for (Integer personalOption : personalOptions) {
                if (!roomOption.getStringedOptions().contains(personalOption.toString())) {
                    containAll = false;
                    break;
                }
            }
            if (containAll) {
                roomsId.add(roomOption.getRoomId());
            }
        }
        return roomsId;
    }

}
