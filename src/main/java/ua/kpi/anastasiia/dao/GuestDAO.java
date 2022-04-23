package ua.kpi.anastasiia.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.kpi.anastasiia.dao.mappers.GuestOptionsMapper;
import ua.kpi.anastasiia.dao.mappers.GuestWithStringedOptionsMapper;
import ua.kpi.anastasiia.dao.mappers.PersonalOptionsMapper;
import ua.kpi.anastasiia.models.guest.Guest;
import ua.kpi.anastasiia.models.guest.GuestOptions;
import ua.kpi.anastasiia.models.guest.PersonalOptions;

import java.util.ArrayList;
import java.util.List;

@Component
public class GuestDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GuestDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Guest> getGuests() {
        return jdbcTemplate.query("SELECT * FROM guests", new BeanPropertyRowMapper<>(Guest.class));
    }

    public List<Guest> getGuestsWithOptions(String search) {
        if (search == null) {
            search = "";
        }
        String querySearch = "%" + search + "%";
        return jdbcTemplate.query("SELECT\n" +
                "       guests.id,\n" +
                "       first_name,\n" +
                "       last_name,\n" +
                "       phone,\n" +
                "       passport,\n" +
                "       email,\n" +
                "       GROUP_CONCAT(po.name SEPARATOR ', ') as group_options\n" +
                "FROM guests\n" +
                "    left join guests_personal_options gpo\n" +
                "        on guests.id = gpo.guest_id\n" +
                "    left join personal_options po\n" +
                "        on gpo.option_id = po.id\n" +
                "WHERE first_name like ? or last_name like ? or phone like ? or email like ?" +
                "GROUP BY \n" +
                "       guests.id,\n" +
                "       first_name,\n" +
                "       last_name,\n" +
                "       phone,\n" +
                "       passport,\n" +
                "       email", new String[]{querySearch, querySearch, querySearch, querySearch}, new GuestWithStringedOptionsMapper());
    }

    public Guest getGuest(int id) {
        return jdbcTemplate.query("SELECT * FROM guests WHERE id =?", new BeanPropertyRowMapper<>(Guest.class), id)
                .stream().findAny().orElse(null);
    }
    public Guest getGuestByPhone(String phone) {
        return jdbcTemplate.query("SELECT * FROM guests WHERE phone =?", new BeanPropertyRowMapper<>(Guest.class), phone)
                .stream().findAny().orElse(null);
    }
    public Guest getGuestByPassport(String passport) {
        return jdbcTemplate.query("SELECT * FROM guests WHERE passport =?", new BeanPropertyRowMapper<>(Guest.class), passport)
                .stream().findAny().orElse(null);
    }
    public Guest getGuestByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM guests WHERE email =?", new BeanPropertyRowMapper<>(Guest.class), email)
                .stream().findAny().orElse(null);
    }

    public List<Integer> getGuestOptionsId(Guest guest) {
        List<PersonalOptions> options = jdbcTemplate.query("select option_id from guests_personal_options where guest_id=?", new PersonalOptionsMapper(), guest.getId());
        List<Integer> optionsId = new ArrayList<>();
        for (PersonalOptions option : options) {
            optionsId.add(option.getId());
        }
        return optionsId;
    }

    public List<String> getGuestOptionsName(Guest guest) {
        List<GuestOptions> guestOptions = jdbcTemplate.query("select po.id, po.name from guests_personal_options left join personal_options po on po.id=option_id where guest_id=?", new GuestOptionsMapper(), guest.getId());
        List<String> result = new ArrayList<String>();
        for (GuestOptions option: guestOptions) {
            result.add(option.getName());
        }
        return result;
    }

    public List<GuestOptions> getGuestOptions() {
       return jdbcTemplate.query("select * from personal_options", new GuestOptionsMapper());
    }

    public void addGuest(Guest guest) {
        jdbcTemplate.update("INSERT INTO guests(first_name, last_name, phone, passport, email) " +
                        "VALUES(?, ?, ?, ?, ?)",
                guest.getFirstName(), guest.getLastName(), guest.getPhone(),
                guest.getPassport(), guest.getEmail());
        for (int i = 0; i < guest.getOptions().size(); i++) {
            jdbcTemplate.update("INSERT INTO guests_personal_options(guest_id, option_id) VALUES (?, ?)", getGuests().get(getGuests().size()-1).getId(), guest.getOptions().get(i));
        }
    }

    public void updateGuest(int id, Guest guest) {
        jdbcTemplate.update("UPDATE guests SET first_name=?, last_name=?, phone=?, passport=?, email=? WHERE id=?",
                guest.getFirstName(), guest.getLastName(), guest.getPhone(), guest.getPassport(), guest.getEmail(), id);
        jdbcTemplate.update("DELETE FROM guests_personal_options WHERE guest_id = ?", guest.getId());
        for (int i = 0; i < guest.getOptions().size(); i++) {
            jdbcTemplate.update("INSERT INTO guests_personal_options(guest_id, option_id) VALUES (?, ?)", guest.getId(), guest.getOptions().get(i));
        }
    }

    public void removeGuest(int id) {
        jdbcTemplate.update("DELETE FROM guests_personal_options WHERE guest_id = ?",id);
        jdbcTemplate.update("DELETE FROM guests WHERE id=?", id);
    }
}
